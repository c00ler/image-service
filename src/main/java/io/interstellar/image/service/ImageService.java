package io.interstellar.image.service;

import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public byte[] generateImage(final String imageFilePrefix, final ChannelMap channelMap) {
        final Collection<File> images = imageRepository.findImages(imageFilePrefix, channelMap);
        // TODO: merge
        // TODO: convert to jpg

        return new byte[0];
    }

}
