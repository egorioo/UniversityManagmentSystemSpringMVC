FROM tomcat:9.0.55-jdk11
RUN rm -rvf /usr/local/tomcat/webapps/ROOT
ADD /target/UniversityManagmentSystemSpringMVC.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh","run"]
EXPOSE 8080