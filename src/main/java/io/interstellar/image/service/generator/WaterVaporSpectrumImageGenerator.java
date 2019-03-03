package io.interstellar.image.service.generator;

import io.interstellar.image.model.ChannelMap;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collection;

@Component
public class WaterVaporSpectrumImageGenerator extends AbstractSpectrumImageGenerator {

    public WaterVaporSpectrumImageGenerator() {
        super(ChannelMap.WATER_VAPOR);
    }

    @Override
    public final byte[] generate(@Nonnull final Collection<File> images) {
        return new byte[0];
    }

}
