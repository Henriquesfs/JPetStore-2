FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src src
RUN mvn -B -q package -DskipTests

FROM tomcat:9.0-jre17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /build/target/jpetstore.war /usr/local/tomcat/webapps/jpetstore.war
COPY --from=build /build/target/jpetstore/WEB-INF/lib/hsqldb-1.8.0.10.jar /opt/hsqldb/hsqldb.jar
COPY db/hsqldb/jpetstore.script db/hsqldb/jpetstore.properties db/hsqldb/server.properties /opt/hsqldb/data/
EXPOSE 8080
CMD ["sh", "-c", "cd /opt/hsqldb/data && java -cp /opt/hsqldb/hsqldb.jar org.hsqldb.Server -database jpetstore & exec catalina.sh run"]
