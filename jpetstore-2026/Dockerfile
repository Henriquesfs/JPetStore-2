FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
COPY src src
RUN mvn -B -q package -DskipTests

FROM tomcat:11.0-jre21-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /build/target/jpetstore.war /usr/local/tomcat/webapps/jpetstore.war
COPY --from=build /build/target/jpetstore/WEB-INF/lib/hsqldb-2.7.4.jar /opt/hsqldb/hsqldb.jar
COPY db/hsqldb/jpetstore.script db/hsqldb/jpetstore.properties db/hsqldb/server.properties /opt/hsqldb/data/
EXPOSE 8080
CMD ["sh", "-c", "cd /opt/hsqldb/data && java -cp /opt/hsqldb/hsqldb.jar org.hsqldb.server.Server & exec catalina.sh run"]
