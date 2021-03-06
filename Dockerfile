FROM maven as build
ADD . /code
WORKDIR /code
RUN mvn package -DskipTests

FROM openjdk:8-jdk-alpine as run
RUN mkdir /app
WORKDIR /app
COPY --from=build /code/target/cnpj-webscraping-0.0.1-SNAPSHOT.jar cnpj-webscraping.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "cnpj-webscraping.jar"]
