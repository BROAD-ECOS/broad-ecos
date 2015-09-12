#!/bin/bash
sleep 5
sed -i 's|/var/www/html|/var/www/learninglocker/public|g' /etc/apache2/sites-available/000-default.conf
sed -i '/learninglocker/a <Directory /var/www/learninglocker>\nAllowoverride All\n</Directory>' /etc/apache2/sites-available/000-default.conf