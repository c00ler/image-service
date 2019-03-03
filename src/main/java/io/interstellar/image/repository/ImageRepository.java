package io.interstellar.image.repository;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final Path dataDirectory;

    @Nonnull
    public Collection<File> findFiles(@Nonnull final String... names) {
        final Set<String> uniqueNames = Arrays.stream(names).collect(Collectors.toSet());
        Validate.isTrue(uniqueNames.stream().allMatch(n -> n.endsWith(".tif")), "Only tif files supported");

        try (final Stream<Path> stream = Files.list(dataDirectory)) {
            final List<File> images = stream
                    .filter(p -> Files.isRegularFile(p) && uniqueNames.contains(toFilename(p)))
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            if (images.size() != uniqueNames.size()) {
                throw new IllegalStateException("Not found");
            }

            return images;
        } catch (IOException e) {
            throw new UncheckedIOException("Exception while searching files", e);
        }
    }

    private static String toFilename(final Path path) {
        return path.getName(path.getNameCount() - 1).toString();
    }

}
