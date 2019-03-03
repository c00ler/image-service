package io.interstellar.image.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final File dataDirectory;

    @Nonnull
    public Collection<File> findFiles(@Nonnull final String... names) {
        final Set<String> uniqueNames = Arrays.stream(names).collect(Collectors.toSet());
        Validate.isTrue(uniqueNames.stream().allMatch(n -> n.endsWith(".tif")), "Only tif files supported");

        final File[] images = dataDirectory.listFiles((dir, name) -> uniqueNames.contains(name));

        // TODO: Improve error handling
        if (images == null || images.length != uniqueNames.size()) {
            throw new IllegalStateException("Not found");
        }

        return Arrays.asList(images);
    }

}
