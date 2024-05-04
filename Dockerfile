FROM openjdk:17-oracle
COPY build/libs/play-ticketing-0.0.1-SNAPSHOT.jar play-ticketing-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-DSpring.profiles.active=prod","-jar","play-ticketing-0.0.1-SNAPSHOT.jar"]