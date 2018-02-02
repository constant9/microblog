FROM mysql:5.7.15

MAINTAINER me

ENV MYSQL_DATABASE=microblog \
    MYSQL_ROOT_PASSWORD=1234

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306