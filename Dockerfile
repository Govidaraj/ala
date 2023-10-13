# mvn clean package

# java -jar target/docker-message-server-1.0.0.jar

FROM openjdk:8-jdk-alpine
MAINTAINER baeldung.com
COPY target/docker-LeaveManagement-0.0.1-SNAPSHOT.jar LeaveManagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/LeaveManagement-0.0.1-SNAPSHOT.jar"]

