FROM openjdk:17-jdk-alpine
MAINTAINER juachdv.com
COPY target/app-test-0.0.1-SNAPSHOT.jar app-test-1.0.0.jar
ENTRYPOINT ["java","-jar","/app-test-1.0.0.jar"]