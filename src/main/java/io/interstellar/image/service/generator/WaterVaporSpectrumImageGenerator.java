package io.interstellar.image.service.generator;

import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.service.GDALInvoker;
import org.springframework.stereotype.Component;

@Component
public class WaterVaporSpectrumImageGenerator extends AbstractSpectrumImageGenerator {

    public WaterVaporSpectrumImageGenerator(final GDALInvoker gdalInvoker) {
        super(gdalInvoker, ChannelMap.WATER_VAPOR);
    }

}
