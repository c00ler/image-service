package io.interstellar.image.service.generator;

import io.interstellar.image.exception.ImageNotFoundException;
import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.service.GDALInvoker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisibleSpectrumImageGeneratorTest {

    @Mock
    private GDALInvoker gdalInvokerMock;

    @InjectMocks
    private VisibleSpectrumImageGenerator underTest;

    @Test
    void shouldThrowIfImageForTheChannelNotFound() throws Exception {
        final File image = new ClassPathResource("sample-granule/T33UUP_20180804T100031_B04.tif").getFile();

        assertThatThrownBy(() -> underTest.generate(Collections.singleton(image)))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessageContaining("No images found")
                .hasMessageContaining(Integer.toString(ChannelMap.VISIBLE.green()));

        verify(gdalInvokerMock, never()).mergeChannels(any(), any(), any());
        verify(gdalInvokerMock, never()).generateImageFromVRT(any());
    }

}
