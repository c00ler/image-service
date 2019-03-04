package io.interstellar.image.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelMapTest {

    @ParameterizedTest(name = "channelMap=''{0}''")
    @CsvSource({"VISIBLE, 4, 3, 2", "VEGETATION, 5, 6, 7", "WATER_VAPOR, , , 9"})
    void shouldMapChannels(final ChannelMap underTest, final Integer red, final Integer green, final Integer blue) {
        assertThat(underTest.red()).isEqualTo(red);
        assertThat(underTest.green()).isEqualTo(green);
        assertThat(underTest.blue()).isEqualTo(blue);
    }

    @Test
    void shouldCreateFromCamelCaseValue() {
        assertThat(ChannelMap.fromString("waterVapor")).isEqualTo(ChannelMap.WATER_VAPOR);
    }

}
