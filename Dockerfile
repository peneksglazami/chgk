FROM tomcat:8.5.31-jre8-alpine
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
COPY target/ChGK-0.7-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war