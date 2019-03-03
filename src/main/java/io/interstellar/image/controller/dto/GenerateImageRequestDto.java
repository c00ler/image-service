package io.interstellar.image.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.model.GridSquare;
import io.interstellar.image.model.LatitudeBand;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Accessors(chain = true)
public class GenerateImageRequestDto {

    private static final String DATE_JSON_FORMAT = "yyyy-MM-dd";

    private static final String DATE_FILENAME_FORMAT = "yyyyMMdd";

    private static final DateTimeFormatter DATE_FILENAME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FILENAME_FORMAT);

    @Digits(integer = 2, fraction = 0)
    @Positive
    @NotNull
    private Integer utmZone;

    @NotNull
    private LatitudeBand latitudeBand;

    @NotNull
    private GridSquare gridSquare;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_JSON_FORMAT)
    @NotNull
    private LocalDate date;

    @NotNull
    private ChannelMap channelMap;

    public String toImageFilePrefix() {
        return String.format("T%d%s%s_%sT",
                utmZone, latitudeBand.name(), gridSquare.name(), date.format(DATE_FILENAME_FORMATTER));
    }

}
