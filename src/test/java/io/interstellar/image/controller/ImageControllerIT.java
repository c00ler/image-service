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
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.Month;

import static io.restassured.RestAssured.given;
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

}