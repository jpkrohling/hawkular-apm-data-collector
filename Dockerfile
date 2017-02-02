FROM openjdk:8-jdk

MAINTAINER Juraci Paixão Kröhling <juraci.docker@kroehling.de>

ENV APP_HOME /app/
RUN mkdir -p  $APP_HOME && chmod 777 $APP_HOME -R
WORKDIR $APP_HOME
USER 10001

EXPOSE 8080
COPY build/install/hawkular-apm-data-collector $APP_HOME
CMD ["/app/bin/hawkular-apm-data-collector", "-b", "0.0.0.0", "-hb", "0.0.0.0"]
