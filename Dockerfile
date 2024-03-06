FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} productQueryService-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/productQueryService-0.0.1-SNAPSHOT.jar"]