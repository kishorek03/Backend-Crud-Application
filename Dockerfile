FROM openjdk:17-jdk-alpine
COPY target/Crud_Application-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "Crud_Application-0.0.1-SNAPSHOT.jar"]



