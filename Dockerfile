FROM tomcat:8.5.31-jre8-alpine
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war