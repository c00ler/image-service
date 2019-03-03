package io.interstellar.image.service.generator;

import io.interstellar.image.model.ChannelMap;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collection;

/**
 * Image generator for a particular channelMap.
 */
public interface SpectrumImageGenerator {

    @Nonnull
    ChannelMap channelMap();

    byte[] generate(@Nonnull Collection<File> images);

}
