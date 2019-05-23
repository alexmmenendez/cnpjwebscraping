FROM openjdk:8-jdk-alpine as run
RUN mkdir /app
WORKDIR /app
COPY ./target/cnpj-webscraping-0.0.1-SNAPSHOT.jar cnpj-webscraping.jar
EXPOSE 8040
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "cnpj-webscraping.jar"]