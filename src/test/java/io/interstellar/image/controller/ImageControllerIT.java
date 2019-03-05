package io.interstellar.image.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.interstellar.image.AbstractIT;
import io.interstellar.image.controller.dto.GenerateImageRequestDto;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.model.GridSquare;
import io.interstellar.image.model.LatitudeBand;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.time.Month;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ImageControllerIT extends AbstractIT {

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldReturn404IfFileNotFound() throws Exception {
        final GenerateImageRequestDto requestBodyDto =
                new GenerateImageRequestDto()
                        .setUtmZone(34)
                        .setLatitudeBand(LatitudeBand.U)
                        .setGridSquare(GridSquare.UP)
                        .setDate(LocalDate.of(2018, Month.AUGUST, 4))
                        .setChannelMap(ChannelMap.VISIBLE);

        given()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(requestBodyDto))
                .when()
                .post("/generate-images")
                .then()
                .log().all()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("title", equalTo("Not Found"))
                .body("details", equalTo("No images found with the prefix: 'T34UUP_20180804T'"));
    }

    @Test
    void shouldGenerateImageForVisibleChannel() throws Exception {
        final GenerateImageRequestDto requestBodyDto =
                new GenerateImageRequestDto()
                        .setUtmZone(33)
                        .setLatitudeBand(LatitudeBand.U)
                        .setGridSquare(GridSquare.UP)
                        .setDate(LocalDate.of(2018, Month.AUGUST, 4))
                        .setChannelMap(ChannelMap.VISIBLE);

        final int expectedSize = 745083;

        final byte[] response =
                given()
                        .accept(ContentType.ANY)
                        .contentType(ContentType.JSON)
                        .body(mapper.writeValueAsString(requestBodyDto))
                        .when()
                        .post("/generate-images")
                        .then()
                        .log().status()
                        .log().headers()
                        .statusCode(200)
                        .header(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.IMAGE_JPEG_VALUE))
                        .header(HttpHeaders.CONTENT_LENGTH, equalTo(Integer.toString(expectedSize)))
                        .extract()
                        .body().asByteArray();

        assertThat(DigestUtils.md5DigestAsHex(response)).isEqualTo("44e58fc1e64dc59d17792cafdcf8f14e");
    }

    @Test
    void shouldGenerateImageForVegetationChannel() throws Exception {
        final GenerateImageRequestDto requestBodyDto =
                new GenerateImageRequestDto()
                        .setUtmZone(33)
                        .setLatitudeBand(LatitudeBand.U)
                        .setGridSquare(GridSquare.UP)
                        .setDate(LocalDate.of(2018, Month.AUGUST, 4))
                        .setChannelMap(ChannelMap.VEGETATION);

        final int expectedSize = 225509;

        final byte[] response =
                given()
                        .accept(ContentType.ANY)
                        .contentType(ContentType.JSON)
                        .body(mapper.writeValueAsString(requestBodyDto))
                        .when()
                        .post("/generate-images")
                        .then()
                        .log().status()
                        .log().headers()
                        .statusCode(200)
                        .header(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.IMAGE_JPEG_VALUE))
                        .header(HttpHeaders.CONTENT_LENGTH, equalTo(Integer.toString(expectedSize)))
                        .extract()
                        .body().asByteArray();

        assertThat(DigestUtils.md5DigestAsHex(response)).isEqualTo("6aab963ef623d23db2103b7ddaa2b79a");
    }

    @Test
    void shouldGenerateImageForWaterVaporChannel() throws Exception {
        final GenerateImageRequestDto requestBodyDto =
                new GenerateImageRequestDto()
                        .setUtmZone(33)
                        .setLatitudeBand(LatitudeBand.U)
                        .setGridSquare(GridSquare.UP)
                        .setDate(LocalDate.of(2018, Month.AUGUST, 4))
                        .setChannelMap(ChannelMap.WATER_VAPOR);

        final int expectedSize = 9064;

        final byte[] response =
                given()
                        .accept(ContentType.ANY)
                        .contentType(ContentType.JSON)
                        .body(mapper.writeValueAsString(requestBodyDto))
                        .when()
                        .post("/generate-images")
                        .then()
                        .log().status()
                        .log().headers()
                        .statusCode(200)
                        .header(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.IMAGE_JPEG_VALUE))
                        .header(HttpHeaders.CONTENT_LENGTH, equalTo(Integer.toString(expectedSize)))
                        .extract()
                        .body().asByteArray();

        assertThat(DigestUtils.md5DigestAsHex(response)).isEqualTo("ba8a5180882d9f1d0c5982d4fa9b57f4");
    }

}
