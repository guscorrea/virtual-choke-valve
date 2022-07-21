FROM openjdk:8-jre-alpine
COPY target/virtual-choke-valve-1.0.0.jar virtual-choke-valve-1.0.0.jar
ENTRYPOINT ["java","-jar","/virtual-choke-valve-1.0.0.jar"]