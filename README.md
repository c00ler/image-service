# Image Service

Generates preview images out of single band GeoTIFF files.

## Dependencies

- Java 11
- [GDAL](https://www.gdal.org/)

## How to run tests

To run only unit tests (GDAL is not required) execute the following command: 

```bash
./mvnw clean test
```

To run unit and integration tests (GDAL is required) execute the following command:

```bash
./mvnw clean verify
```

## How to run service locally

Build an executable jar using the following command:

```bash
./mvnw clean package
```

Run it using the following command:

```bash
java -Ddata.directory=<path_to_directory_with_images> -jar target/service.jar
```

By default server is running on port `8080`.


## How to run service with sample data

Service can be started using sample data, that is used for integration testing:

```bash
java -Ddata.directory=src/test/resources/sample-granule -jar target/service.jar
```

Try calling the service using the following command:

```bash
http --verbose :8080/generate-images utmZone=33 latitudeBand=U gridSquare=UP date="2018-08-04" channelMap=vegetation
```

## How to run service in docker

For easier testing it's possible to run service in docker. 
First build an image:

```bash
docker build -t image-service:latest .
```

**First run may take some time. Provided image is not optimized for any kind of workload 
and can only be used for testing.**

Start the container using the following command:

```bash
docker run --rm -it -v <path_to_directory_with_images>:/tmp/granules -p 8080:8080 image-service:latest
```
