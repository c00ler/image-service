package io.interstellar.image.repository;

import io.interstellar.image.AbstractIT;
import io.interstellar.image.exception.ImageNotFoundException;
import io.interstellar.image.model.ChannelMap;
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

        assertThatThrownBy(() -> underTest.findImages(prefix, ChannelMap.VISIBLE))
                .isInstanceOf(ImageNotFoundException.class)
                .hasMessageContaining("No images found")
                .hasMessageContaining(prefix);
    }

    @Test
    void findImagesShouldFindAllRequestFiles() {
        final Collection<File> files = underTest.findImages("T33UUP_20180804T", ChannelMap.VISIBLE);

        assertThat(files).extracting(File::getName).containsExactlyInAnyOrder(
                "T33UUP_20180804T100031_B02.tif", "T33UUP_20180804T100031_B03.tif", "T33UUP_20180804T100031_B04.tif");
    }

}
