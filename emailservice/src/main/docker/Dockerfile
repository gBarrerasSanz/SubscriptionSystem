FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} emailservice.jar
RUN sh -c 'touch /emailservice.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/emailservice.jar"]
#CMD ["sh", "-c", "tail -f /dev/null"]