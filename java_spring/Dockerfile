FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY ./app/pom.xml ./pom.xml
COPY ./app/src ./src

RUN mvn -B clean package -DskipTests

FROM eclipse-temurin:17-jre

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /build/target/benchring_java.jar ./benchring_java.jar

EXPOSE 8080

CMD ["java", "-jar", "benchring_java.jar"]
