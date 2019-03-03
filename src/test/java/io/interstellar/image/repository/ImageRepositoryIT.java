package io.interstellar.image.repository;

import io.interstellar.image.AbstractIT;
import io.interstellar.image.exception.ImageNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImageRepositoryIT extends AbstractIT {

    @Autowired
    private ImageRepository underTest;

    @Test
    void findImagesShouldThrowIfNoFilesFoundForPrefix() {
        final String prefix = "T33UUP_20180805T";

        assertThatThrownBy(() -> underTest.findImages(prefix))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessageContaining("No images found")
                .hasMessageContaining(prefix);
    }

    @Test
    void findImagesShouldFindAllFilesForRequestPrefix() {
        final Collection<File> files = underTest.findImages("T33UUP_20180804T");

        assertThat(files).extracting(File::getName)
                .containsExactlyInAnyOrder(
                        "T33UUP_20180804T100031_B01.tif",
                        "T33UUP_20180804T100031_B02.tif",
                        "T33UUP_20180804T100031_B03.tif",
                        "T33UUP_20180804T100031_B04.tif",
                        "T33UUP_20180804T100031_B05.tif",
                        "T33UUP_20180804T100031_B06.tif",
                        "T33UUP_20180804T100031_B07.tif",
                        "T33UUP_20180804T100031_B8A.tif",
                        "T33UUP_20180804T100031_B08.tif",
                        "T33UUP_20180804T100031_B09.tif",
                        "T33UUP_20180804T100031_B10.tif",
                        "T33UUP_20180804T100031_B11.tif",
                        "T33UUP_20180804T100031_B12.tif");
    }

}
