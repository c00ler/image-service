package io.interstellar.image.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@Slf4j
public class DataDirectoryConfiguration {

    @Bean
    public File dataDirectory(@Value("${data.directory}") final String dataDirectory) throws FileNotFoundException {
        final File dataDirectoryFile = ResourceUtils.getFile(dataDirectory);

        Validate.validState(dataDirectoryFile.exists(), "Directory '%s' does not exist", dataDirectory);
        Validate.validState(dataDirectoryFile.isDirectory(), "'%s' is not a directory", dataDirectory);

        LOG.info("Data directory: {}", dataDirectoryFile.getAbsolutePath());

        return dataDirectoryFile;
    }

}
