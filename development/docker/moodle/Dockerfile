FROM php:fpm
MAINTAINER Welington Veiga <welington.veiga@gmail.com>

# PHP config
ADD ./php.ini /usr/local/etc/php/

# Install modules
# Install PHP modules
RUN apt-get update \
&& apt-get install -y zlib1g-dev \
 libxml2-dev \
 libpng12-dev \
 libfreetype6-dev \
 libjpeg62-turbo-dev \
 libicu-dev \
 libcurl4-gnutls-dev \
 g++ \
 ghostscript \
 graphviz \
 aspell \
&& rm -r /var/lib/apt/lists/* \
&& docker-php-ext-configure gd \
 --with-freetype-dir=/usr/include/ \
 --with-gd-dir=usr/include/ \
&& docker-php-ext-install zip \
 mysqli \
 gd \
 mbstring \
 xmlrpc \
 soap \
 intl \
 opcache \
 curl

# Download and placing moodle
RUN curl -O https://download.moodle.org/download.php/direct/stable29/moodle-latest-29.tgz \
&& tar -xzf moodle-latest-29.tgz -C /var/www \
&& rm -Rf /var/www/html/moodle/ \
&& mv /var/www/moodle /var/www/html/moodle \
&& rm  /var/www/html/moodle/config-dist.php \
&& mkdir -p  /var/www/moodledata \
&& chmod -R 0777 /var/www/ \
&& chown -R root:root /var/www/

VOLUME /var/www/html
CMD ["php-fpm"]
