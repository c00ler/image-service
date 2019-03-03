package io.interstellar.image.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
@AllArgsConstructor
public final class ErrorResponseDto {

    int status;

    String title;

    String details;

    public ErrorResponseDto(final HttpStatus status, final String details) {
        this(status.value(), status.getReasonPhrase(), details);
    }

}
