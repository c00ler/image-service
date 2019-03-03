package io.interstellar.image.service;

import io.interstellar.image.model.ChannelMap;
import io.interstellar.image.repository.ImageRepository;
import io.interstellar.image.service.generator.SpectrumImageGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepositoryMock;

    @Mock
    private SpectrumImageGenerator visibleSpectrumImageGeneratorMock;

    private ImageService underTest;

    @BeforeEach
    void beforeEach() {
        given(visibleSpectrumImageGeneratorMock.channelMap()).willReturn(ChannelMap.VISIBLE);

        underTest = new ImageService(imageRepositoryMock, Collections.singleton(visibleSpectrumImageGeneratorMock));
    }

    @Test
    void shouldThrowIfGeneratorNotFound() {
        assertThatThrownBy(() -> underTest.generateImage("test", ChannelMap.VEGETATION))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(ChannelMap.VEGETATION.name())
                .hasMessageContaining("not supported");

        verify(imageRepositoryMock, never()).findImages(anyString());
    }

    @Test
    void shouldDelegateGenerationToImageGenerator() throws Exception {
        final String prefix = "test";

        final File image = new ClassPathResource("sample-granule/T33UUP_20180804T100031_B01.tif").getFile();
        final Set<File> images = Collections.singleton(image);

        final byte[] expectedBytes = new byte[8];
        ThreadLocalRandom.current().nextBytes(expectedBytes);

        given(imageRepositoryMock.findImages(prefix)).willReturn(images);
        given(visibleSpectrumImageGeneratorMock.generate(images)).willReturn(expectedBytes);

        final byte[] result = underTest.generateImage(prefix, ChannelMap.VISIBLE);
        assertThat(result).isSameAs(expectedBytes);
    }

}
