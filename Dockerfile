FROM openjdk:8-jdk-alpine
VOLUME /tmp
#ARG JAR_FILE=target/ethanpetstore-0.0.1-SNAPSHOT.war
#一个正常运行的demo war包，开发时可替换成target目录下的war包
ARG JAR_FILE=demowar/ethanpetstore-0.0.1-SNAPSHOT.war
ADD ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]