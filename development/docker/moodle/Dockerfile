FROM php:fpm
MAINTAINER Welington Veiga <welington.veiga@gmail.com>

# PHP config
ADD ./php.ini /usr/local/etc/php/

# Install modules
RUN apt-get update \
    && apt-get install -y \
        php5-xdebug \
        php5-intl \
    && docker-php-ext-install \
        pdo_mysql \
        mbstring \
        opcache

RUN curl -O https://download.moodle.org/download.php/direct/stable29/moodle-latest-29.tgz \
&& tar -xzf moodle-latest-29.tgz -C /var/www \
&& rm -Rf /var/www/html/moodle \
&& mv /var/www/moodle /var/www/html/moodle \
&& chmod -R 0777 /var/www/html/moodle \
&& chown -R root:root /var/www/html/moodle

VOLUME /var/www/html
CMD ["php-fpm"]
