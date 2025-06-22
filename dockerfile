# -------------------------------------
# Dockerfile for springboot
# -------------------------------------
FROM amazoncorretto:21-alpine-jdk
ARG jarName
EXPOSE 8080

ENV jarNameEnv=${jarName}

COPY $jarNameEnv /.
COPY /healthcheck.sh /.
RUN mv $jarNameEnv app.jar
RUN chmod a+x ./healthcheck.sh
HEALTHCHECK --interval=30s --timeout=15s --start-period=300s CMD ./healthcheck.sh localhost 8080 200 /actuator/health

CMD [ "java", "-jar", "/app.jar"]
