FROM java:8
VOLUME /tmp
ADD config/ /opt/config
RUN cd /opt
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar", "--spring.config.location=file:///opt/config/application.properties"]
