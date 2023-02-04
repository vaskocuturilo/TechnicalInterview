FROM openjdk:8-jdk-alpine
VOLUME /main-app
ADD build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]