FROM amazoncorretto:21-alpine-jdk
EXPOSE 8080
ARG JAR_FILE=target/mock-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]