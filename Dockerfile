FROM java:8
VOLUME /tmp
ADD target/userapi-0.0.1-SNAPSHOT.jar target/app.jar
RUN bash -c 'touch target/app.jar'
ENV JAVA_OPTS="-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8787,suspend=n"
EXPOSE 8080 8787
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","target/app.jar"]
