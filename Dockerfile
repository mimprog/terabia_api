FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/terabia-0.0.1-SNAPSHOT.jar terabia.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "terabia.jar"]
