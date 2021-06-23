FROM openjdk:16-alpine
LABEL maintainer="andersson.albert@gmail.com"
ADD /target/covid-tweeter-0.0.1-SNAPSHOT.jar covid-tweeter.jar
EXPOSE 5051
ENTRYPOINT ["java", "-jar","/covid-tweeter.jar"]