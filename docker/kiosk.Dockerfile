# Image of the gate kiosk app (edge quantum, ADR-03). On real kiosks it runs on the device itself.
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN mvn -q -B -pl kiosk/kiosk-app -am package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/kiosk/kiosk-app/target/kiosk-app-*.jar app.jar
VOLUME /app/data
ENV KIOSK_DATA_DIR=/app/data
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
