FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/ethanpetstore-0.0.1-SNAPSHOT.war
ADD ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]