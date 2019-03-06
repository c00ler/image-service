FROM adoptopenjdk/openjdk11:alpine

RUN apk update \
    && apk add gdal --repository http://dl-cdn.alpinelinux.org/alpine/edge/testing/ \
    && apk add py-gdal --repository http://dl-cdn.alpinelinux.org/alpine/edge/testing/

ENV PRJDIR /usr/src/dev

COPY ./pom.xml $PRJDIR/pom.xml
COPY ./.mvn $PRJDIR/.mvn
COPY ./mvnw $PRJDIR/mvnw

WORKDIR $PRJDIR

RUN ./mvnw dependency:go-offline -B

COPY ./src $PRJDIR/src
COPY ./lombok.config $PRJDIR/lombok.config

RUN ./mvnw clean package

EXPOSE 8080

CMD ["java", "-Duse.gdal.merge=true", "-jar", "./target/service.jar"]
