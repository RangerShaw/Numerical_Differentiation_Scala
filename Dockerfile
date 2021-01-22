FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY . /app

ENTRYPOINT ["java", "-jar", "./out/Scala.jar"]