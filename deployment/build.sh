#!/bin/bash -e
cd mysql
docker build -t mysqlimage_costa .
docker run -d -p 4406:3306 mysqlimage_costa
cd ../..
mvn clean install
cd microblog-app/target/
java -jar microblog-app-1.0-SNAPSHOT.jar