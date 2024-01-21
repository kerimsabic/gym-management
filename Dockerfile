FROM maven:3.9.2 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19-jdk
COPY --from=build /target/gym-0.0.1-SNAPSHOT.jar gym.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","gym.jar"]
