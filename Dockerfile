FROM openjdk:11

WORKDIR /findog

COPY test-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]