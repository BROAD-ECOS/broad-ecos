#!/bin/bash
set -e

# set this env variable to true to enable a line in the
# pg_hba.conf file to trust samenet.  this can be used to connect
# from other containers on the same host without authentication
PSQL_TRUST_LOCALNET=${PSQL_TRUST_LOCALNET:-false}

DB_NAME=${DB_NAME:-}
DB_USER=${DB_USER:-}
DB_PASS=${DB_PASS:-}
DB_UNACCENT=${DB_UNACCENT:false}

# by default postgresql will start up as a standalone instance.
# set this environment variable to master, slave or snapshot to use replication features.
# "snapshot" will create a point in time backup of a master instance.
PSQL_MODE=${PSQL_MODE:-standalone}

REPLICATION_USER=${REPLICATION_USER:-}
REPLICATION_PASS=${REPLICATION_PASS:-}
REPLICATION_HOST=${REPLICATION_HOST:-}
REPLICATION_PORT=${REPLICATION_PORT:-5432}

# set this env variable to "require" to enable encryption and "verify-full" for verification.
PSQL_SSLMODE=${PSQL_SSLMODE:-disable}

## Adapt uid and gid for ${PG_USER}:${PG_USER}
USERMAP_ORIG_UID=$(id -u ${PG_USER})
USERMAP_ORIG_GID=$(id -g ${PG_USER})
USERMAP_GID=${USERMAP_GID:-${USERMAP_UID:-$USERMAP_ORIG_GID}}
USERMAP_UID=${USERMAP_UID:-$USERMAP_ORIG_UID}
if [[ ${USERMAP_UID} != ${USERMAP_ORIG_UID} ]] || [[ ${USERMAP_GID} != ${USERMAP_ORIG_GID} ]]; then
  echo "Adapting uid and gid for ${PG_USER}:${PG_USER} to $USERMAP_UID:$USERMAP_GID"
  groupmod -g ${USERMAP_GID} ${PG_USER}
  sed -i -e "s/:${USERMAP_ORIG_UID}:${USERMAP_GID}:/:${USERMAP_UID}:${USERMAP_GID}:/" /etc/passwd
fi

# fix ownership of ${PG_CONFDIR} (may be necessary if USERMAP_* was set)
chown -R ${PG_USER}:${PG_USER} ${PG_CONFDIR}

# fix permissions and ownership of ${PG_HOME}
mkdir -p -m 0700 ${PG_HOME}
chown -R ${PG_USER}:${PG_USER} ${PG_HOME}

# fix permissions and ownership of /run/postgresql
mkdir -p -m 0755 /run/postgresql /run/postgresql/${PG_VERSION}-main.pg_stat_tmp
chown -R ${PG_USER}:${PG_USER} /run/postgresql
chmod g+s /run/postgresql

if [[ ${PSQL_SSLMODE} == disable ]]; then
  sed 's/ssl = true/#ssl = true/' -i ${PG_CONFDIR}/postgresql.conf
fi

# Change DSM from `posix' to `sysv' if we are inside an lx-brand container
if [[ $(uname -v) == "BrandZ virtual linux" ]]; then
  sed 's/\(dynamic_shared_memory_type = \)posix/\1sysv/' \
    -i ${PG_CONFDIR}/postgresql.conf
fi

# listen on all interfaces
cat >> ${PG_CONFDIR}/postgresql.conf <<EOF
listen_addresses = '*'
EOF

if [[ ${PSQL_TRUST_LOCALNET} == true ]]; then
  echo "Enabling trust samenet in pg_hba.conf..."
  cat >> ${PG_CONFDIR}/pg_hba.conf <<EOF
host    all             all             samenet                 trust
EOF
fi

# allow remote connections to postgresql base
cat >> ${PG_CONFDIR}/pg_hba.conf <<EOF
host    all             all             0.0.0.0/0               md5
EOF

# allow replication connections to the base
if [[ -n ${REPLICATION_USER} ]]; then
  if [[ ${PSQL_SSLMODE} == disable ]]; then
    cat >> ${PG_CONFDIR}/pg_hba.conf <<EOF
host    replication     $REPLICATION_USER       0.0.0.0/0               md5
EOF
  else
    cat >> ${PG_CONFDIR}/pg_hba.conf <<EOF
hostssl replication     $REPLICATION_USER       0.0.0.0/0               md5
EOF
  fi
fi

if [[ ${PSQL_MODE} == master ]]; then
  if [[ -n ${REPLICATION_USER} ]]; then
    echo "Supporting hot standby..."
    cat >> ${PG_CONFDIR}/postgresql.conf <<EOF
wal_level = hot_standby
max_wal_senders = 3
checkpoint_segments = 8
wal_keep_segments = 8
EOF
  fi
fi

cd ${PG_HOME}

# initialize PostgreSQL data directory
if [[ ! -d ${PG_DATADIR} ]]; then
  if [[ ${PSQL_MODE} == slave || ${PSQL_MODE} == snapshot ]]; then
    echo "Replicating database..."
    if [[ ${PSQL_MODE} == snapshot ]]; then
      sudo -Hu ${PG_USER} \
        PGPASSWORD=$REPLICATION_PASS ${PG_BINDIR}/pg_basebackup -D ${PG_DATADIR} \
        -h ${REPLICATION_HOST} -p ${REPLICATION_PORT} -U ${REPLICATION_USER} -w -x -v -P
    elif [[ ${PSQL_MODE} == slave ]]; then
      # Setup streaming replication.
      sudo -Hu ${PG_USER} \
        PGPASSWORD=$REPLICATION_PASS ${PG_BINDIR}/pg_basebackup -D ${PG_DATADIR} \
        -h ${REPLICATION_HOST} -p ${REPLICATION_PORT} -U ${REPLICATION_USER} -w -v -P
      echo "Setting up hot standby configuration..."
      cat >> ${PG_CONFDIR}/postgresql.conf <<EOF
hot_standby = on
EOF
      sudo -Hu ${PG_USER} touch ${PG_DATADIR}/recovery.conf
      cat >> ${PG_DATADIR}/recovery.conf <<EOF
standby_mode = 'on'
primary_conninfo = 'host=${REPLICATION_HOST} port=${REPLICATION_PORT} user=${REPLICATION_USER} password=${REPLICATION_PASS} sslmode=${PSQL_SSLMODE}'
trigger_file = '/tmp/postgresql.trigger'
EOF
    fi

  else
    # check if we need to perform data migration
    PG_OLD_VERSION=$(find ${PG_HOME}/[0-9].[0-9]/main -maxdepth 1 -name PG_VERSION 2>/dev/null | sort -r | head -n1 | cut -d'/' -f5)

    echo "Initializing database..."
    sudo -Hu ${PG_USER} ${PG_BINDIR}/initdb --pgdata=${PG_DATADIR} \
      --username=${PG_USER} --encoding=unicode --auth=trust >/dev/null
  fi
fi

if [[ -n ${PG_OLD_VERSION} ]]; then
  echo "Migrating postgresql ${PG_OLD_VERSION} data..."
  PG_OLD_CONFDIR="/etc/postgresql/${PG_OLD_VERSION}/main"
  PG_OLD_BINDIR="/usr/lib/postgresql/${PG_OLD_VERSION}/bin"
  PG_OLD_DATADIR="${PG_HOME}/${PG_OLD_VERSION}/main"

  # backup ${PG_OLD_DATADIR} to avoid data loss
  PG_BKP_SUFFIX=$(date +%Y%m%d%H%M%S)
  echo "Backing up ${PG_OLD_DATADIR} to ${PG_OLD_DATADIR}.${PG_BKP_SUFFIX}..."
  cp -a ${PG_OLD_DATADIR} ${PG_OLD_DATADIR}.${PG_BKP_SUFFIX}

  echo "Installing postgresql-${PG_OLD_VERSION}..."
  apt-get update
  apt-get install postgresql-${PG_OLD_VERSION} postgresql-client-${PG_OLD_VERSION}
  rm -rf /var/lib/apt/lists/*

  # migrate ${PG_OLD_VERSION} data
  echo "Migration in progress. This could take a while, please be patient..."
  sudo -Hu ${PG_USER} ${PG_BINDIR}/pg_upgrade \
    -b ${PG_OLD_BINDIR} -B ${PG_BINDIR} \
    -d ${PG_OLD_DATADIR} -D ${PG_DATADIR} \
    -o "-c config_file=${PG_OLD_CONFDIR}/postgresql.conf" \
    -O "-c config_file=${PG_CONFDIR}/postgresql.conf" >/dev/null
fi

# Hot standby (slave and snapshot) servers can ignore the following code.
if [[ ${PSQL_MODE} == standalone || ${PSQL_MODE} == master ]]; then
  if [[ -n ${REPLICATION_USER} ]]; then
    if [[ -z ${REPLICATION_PASS} ]]; then
      echo ""
      echo "WARNING: "
      echo "  Please specify a password for replication user \"${REPLICATION_USER}\". Skipping user creation..."
      echo ""
      DB_USER=
    else
      echo "Creating user \"${REPLICATION_USER}\"..."
      echo "CREATE ROLE ${REPLICATION_USER} WITH REPLICATION LOGIN ENCRYPTED PASSWORD '${REPLICATION_PASS}';" |
        sudo -Hu ${PG_USER} ${PG_BINDIR}/postgres --single \
          -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf >/dev/null
    fi
  fi

  if [[ -n ${DB_USER} ]]; then
    if [[ -z ${DB_PASS} ]]; then
      echo ""
      echo "WARNING: "
      echo "  Please specify a password for \"${DB_USER}\". Skipping user creation..."
      echo ""
      DB_USER=
    else
      echo "Creating user \"${DB_USER}\"..."
      echo "CREATE ROLE ${DB_USER} with LOGIN CREATEDB PASSWORD '${DB_PASS}';" |
        sudo -Hu ${PG_USER} ${PG_BINDIR}/postgres --single \
          -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf >/dev/null
    fi
  fi

  if [[ -n ${DB_NAME} ]]; then
    for db in $(awk -F',' '{for (i = 1 ; i <= NF ; i++) print $i}' <<< "${DB_NAME}"); do
      echo "Creating database \"${db}\"..."
      echo "CREATE DATABASE ${db};" | \
        sudo -Hu ${PG_USER} ${PG_BINDIR}/postgres --single \
          -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf >/dev/null

      if [[ ${DB_UNACCENT} == true ]]; then
        echo "Installing unaccent extension..."
        echo "CREATE EXTENSION IF NOT EXISTS unaccent;" | \
          sudo -Hu ${PG_USER} ${PG_BINDIR}/postgres --single ${db} \
            -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf >/dev/null
      fi

      if [[ -n ${DB_USER} ]]; then
        echo "Granting access to database \"${db}\" for user \"${DB_USER}\"..."
        echo "GRANT ALL PRIVILEGES ON DATABASE ${db} to ${DB_USER};" |
          sudo -Hu ${PG_USER} ${PG_BINDIR}/postgres --single \
            -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf >/dev/null
      fi
    done
  fi
fi

echo "Starting PostgreSQL server..."
exec start-stop-daemon --start --chuid ${PG_USER}:${PG_USER} --exec ${PG_BINDIR}/postgres -- \
  -D ${PG_DATADIR} -c config_file=${PG_CONFDIR}/postgresql.conf
