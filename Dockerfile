FROM openjdk:17

# WORKDIR /findog

COPY findog-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]