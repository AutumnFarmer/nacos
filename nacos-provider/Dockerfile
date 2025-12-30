FROM eclipse-temurin:17-jre
VOLUME /tmp
COPY target/nacos-provider-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
