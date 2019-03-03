package io.interstellar.image.controller;

import io.interstellar.image.controller.dto.ErrorResponseDto;
import io.interstellar.image.exception.ImageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ErrorResponseDto handleImageNotFoundException(final ImageNotFoundException e) {
        LOG.error("Exception occurred during request processing", e);

        return new ErrorResponseDto(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
