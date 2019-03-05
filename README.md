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

By default server is running on the port `8080`.


## How to run service with sample data

Service can be started using sample data, that is used for integration testing:

```bash
java -Ddata.directory=src/test/resources/sample-granule -jar target/service.jar
```

Try calling the service using the following command:

```bash
http --verbose :8080/generate-images utmZone=33 latitudeBand=U gridSquare=UP date="2018-08-04" channelMap=vegetation
```
