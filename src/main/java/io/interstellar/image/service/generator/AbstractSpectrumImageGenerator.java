package io.interstellar.image.service.generator;

import com.google.common.base.Stopwatch;
import io.interstellar.image.exception.ImageNotFoundException;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.service.GDALInvoker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
abstract class AbstractSpectrumImageGenerator implements SpectrumImageGenerator {

    private final ChannelMap channelMap;

    protected final GDALInvoker gdalInvoker;

    AbstractSpectrumImageGenerator(final GDALInvoker gdalInvoker, final ChannelMap channelMap) {
        this.gdalInvoker = gdalInvoker;
        this.channelMap = channelMap;
    }

    @Nonnull
    @Override
    public final ChannelMap channelMap() {
        return channelMap;
    }

    /**
     * Default implementation generates an image from the combination of 3 channels.
     *
     * @param images collection of images with the same prefix
     * @return generated image
     */
    @Override
    public byte[] generate(@Nonnull final Collection<File> images) {
        final File redChannel = getFileForChannel(images, channelMap().red());
        final File greenChannel = getFileForChannel(images, channelMap().green());
        final File blueChannel = getFileForChannel(images, channelMap().blue());

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final File mergedChannelsFile = gdalInvoker.mergeChannels(redChannel, greenChannel, blueChannel);
        LOG.info("intermediate file successfully generated in {} ms: {}",
                stopwatch.elapsed(TimeUnit.MILLISECONDS), mergedChannelsFile.getAbsolutePath());

        stopwatch.reset();
        final File jpgFile = gdalInvoker.generateImage(mergedChannelsFile);
        LOG.info("jpg file successfully generated in {} ms: {}",
                stopwatch.elapsed(TimeUnit.MILLISECONDS), jpgFile.getAbsolutePath());

        final byte[] bytes = toBytes(jpgFile);

        FileUtils.deleteQuietly(mergedChannelsFile);
        FileUtils.deleteQuietly(jpgFile);

        return bytes;
    }

    protected final File getFileForChannel(final Collection<File> images, final Integer channel) {
        Objects.requireNonNull(channel, "channel must not be null");

        final String suffix = String.format("B%02d.tif", channel);
        return images.stream()
                .filter(i -> i.getName().endsWith(suffix))
                .findFirst()
                .orElseThrow(() -> new ImageNotFoundException("No images found for the channel '%d'", channel));
    }

    protected final byte[] toBytes(final File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Exception while reading the file: %s", file.getAbsolutePath()), e);
        }
    }

}
