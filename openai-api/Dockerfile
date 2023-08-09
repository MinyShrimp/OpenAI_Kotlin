FROM openjdk:21-ea-17-slim-buster

RUN mkdir /app
COPY build/libs/openai-api-1.0.0.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
