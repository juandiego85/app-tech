FROM openjdk:17-jdk-alpine
MAINTAINER juachdv.com
ARG JAR_FILE=target/app-test-0.0.1-SNAPSHOT.jar
COPY  ${JAR_FILE} apptest.jar
ENTRYPOINT ["java","-jar","/apptest.jar"]
