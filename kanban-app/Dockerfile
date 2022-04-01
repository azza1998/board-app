FROM maven:3.8-openjdk-11 as build
WORKDIR /app
COPY pom.xml /app
RUN mvn -f pom.xml clean package


FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar  /app/app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar app.jar"]
