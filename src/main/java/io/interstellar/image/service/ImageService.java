package io.interstellar.image.service;

import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public byte[] generateImage(final String imageFilePrefix, final ChannelMap channelMap) {
        return new byte[0];
    }

}
