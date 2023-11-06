ARG DOCKER
FROM $DOCKER
ARG APP_VER
ENV VER=${APP_VER}
ENV JAVA_OPTS=""
WORKDIR /application
COPY rococo-pictures-$VER.jar ./

EXPOSE 8084
CMD java -jar -Dspring.profiles.active=docker $JAVA_OPTS rococo-pictures-$VER.jar
