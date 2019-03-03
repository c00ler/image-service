package io.interstellar.image.repository;

import io.interstellar.image.AbstractIT;
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
    void findFilesShouldThrowIfNotAllNamesAreTif() {
        final String[] names = new String[]{
                "T33UUP_20180804T100031_B01.tif",
                "T33UUP_20180804T100031_B02.tif",
                "T33UUP_20180804T100031_B03.jpg"};

        assertThatThrownBy(() -> underTest.findFiles(names))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Only tif");
    }

    @Test
    void findFilesShouldFindAllRequestFiles() {
        final String[] names = new String[]{
                "T33UUP_20180804T100031_B02.tif",
                "T33UUP_20180804T100031_B03.tif",
                "T33UUP_20180804T100031_B04.tif"};

        final Collection<File> files = underTest.findFiles(names);

        assertThat(files).hasSize(3).allMatch(File::isFile);
    }

    @Test
    void findFilesShouldThrowIfNotAllFilesFound() {
        final String[] names = new String[]{
                "T33UUP_20180804T100031_B02.tif",
                "T33UUP_20180804T100031_B03.tif",
                "T33UUP_20180804T100031_B13.tif"};

        assertThatThrownBy(() -> underTest.findFiles(names))
                .isInstanceOf(IllegalStateException.class);
    }

}
