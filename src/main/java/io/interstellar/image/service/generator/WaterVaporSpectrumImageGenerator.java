package io.interstellar.image.service.generator;

import com.google.common.base.Stopwatch;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.service.GDALInvoker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class WaterVaporSpectrumImageGenerator extends AbstractSpectrumImageGenerator {

    public WaterVaporSpectrumImageGenerator(final GDALInvoker gdalInvoker) {
        super(gdalInvoker, ChannelMap.WATER_VAPOR);
    }

    @Override
    public byte[] generate(@Nonnull final Collection<File> images) {
        final File blueChannel = getFileForChannel(images, channelMap().blue());

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final File jpgFile = gdalInvoker.generateImageFromSingleChannel(blueChannel);
        LOG.info("jpg file successfully generated in {} ms: {}",
                stopwatch.elapsed(TimeUnit.MILLISECONDS), jpgFile.getAbsolutePath());

        final byte[] bytes = toBytes(jpgFile);

        FileUtils.deleteQuietly(jpgFile);

        return bytes;
    }

}
