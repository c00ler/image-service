package io.interstellar.image.repository;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ImageFileClassifierTest {

    @Test
    void shouldExtractTimeComponent() throws Exception {
        final File image = new ClassPathResource("sample-granule/T33UUP_20180804T100031_B01.tif").getFile();

        final ImageFileClassifier underTest = new ImageFileClassifier("T33UUP_20180804T");

        assertThat(underTest.apply(image)).isEqualTo(100031);
    }

}
