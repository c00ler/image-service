package io.interstellar.image.controller;

import io.interstellar.image.controller.dto.GenerateImageRequestDto;
import io.interstellar.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/generate-images", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] generateImage(@Valid @RequestBody final GenerateImageRequestDto requestBody) {
        return imageService.generateImage(requestBody.toImageFilePrefix(), requestBody.getChannelMap());
    }

}
