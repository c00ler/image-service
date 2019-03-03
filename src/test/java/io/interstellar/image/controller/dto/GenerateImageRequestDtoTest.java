package io.interstellar.image.controller.dto;

import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.model.GridSquare;
import io.interstellar.image.model.LatitudeBand;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateImageRequestDtoTest {

    @Test
    void shouldCreateImageFilePrefix() {
        final GenerateImageRequestDto requestDto =
                new GenerateImageRequestDto()
                        .setUtmZone(33)
                        .setLatitudeBand(LatitudeBand.U)
                        .setGridSquare(GridSquare.UP)
                        .setDate(LocalDate.of(2018, Month.AUGUST, 4))
                        .setChannelMap(ChannelMap.VISIBLE);

        assertThat(requestDto.toImageFilePrefix()).isEqualTo("T33UUP_20180804T");
    }

}
