FROM tomcat:8.0.44-jre7-alpine
ADD target/ChGK-0.7-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war