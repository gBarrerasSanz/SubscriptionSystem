FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} eventservice.jar
RUN sh -c 'touch /eventservice.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eventservice.jar"]
#CMD ["sh", "-c", "tail -f /dev/null"]