package io.interstellar.image.service;

import com.google.common.collect.Maps;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.repository.ImageRepository;
import io.interstellar.image.service.generator.SpectrumImageGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Map;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    private final Map<ChannelMap, SpectrumImageGenerator> generators;

    public ImageService(final ImageRepository imageRepository, final Collection<SpectrumImageGenerator> generators) {
        this.imageRepository = imageRepository;
        this.generators = Maps.uniqueIndex(generators, SpectrumImageGenerator::channelMap);
    }

    public byte[] generateImage(final String imageFilePrefix, final ChannelMap channelMap) {
        final SpectrumImageGenerator imageGenerator = generators.get(channelMap);
        if (imageGenerator == null) {
            throw new IllegalStateException(String.format("Channel map '%s' is not supported", channelMap.name()));
        }

        final Collection<File> images = imageRepository.findImages(imageFilePrefix);
        return imageGenerator.generate(images);
    }

}
