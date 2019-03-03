package io.interstellar.image.service.generator;

import io.interstellar.image.model.ChannelMap;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Set;

abstract class AbstractSpectrumImageGenerator implements SpectrumImageGenerator {

    private final ChannelMap channelMap;

    AbstractSpectrumImageGenerator(final ChannelMap channelMap) {
        this.channelMap = channelMap;
    }

    @Nonnull
    @Override
    public final ChannelMap channelMap() {
        return channelMap;
    }

//    channelMap.channels()
//            .stream()
//                .map(c -> {
//        final File file = getFileForChannel(images, c);
//        if (file == null) {
//            throw new ImageNotFoundException(
//                    "No images found with the prefix '%s' and channel '%d'", prefix, c);
//        }
//        return file;
//    }).collect(Collectors.toSet());

    protected final File getFileForChannel(final Set<File> images, final int channel) {
        final String suffix = String.format("B%02d.tif", channel);
        return images.stream().filter(i -> i.getName().endsWith(suffix)).findFirst().orElse(null);
    }

}
