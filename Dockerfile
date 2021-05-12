FROM openjdk:11

USER root
WORKDIR /app/

COPY build/libs/monitor-*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]

#docker build -t hataketed/ws-monitor:2.0.1 .
#docker push hataketed/ws-monitor:2.0.1
