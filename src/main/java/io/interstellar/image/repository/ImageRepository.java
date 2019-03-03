package io.interstellar.image.repository;

import io.interstellar.image.exception.ImageNotFoundException;
import io.interstellar.image.model.ChannelMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: Requires design improvement to facilitate testing
@Repository
@Slf4j
@RequiredArgsConstructor
public class ImageRepository {

    private final Path dataDirectory;

    @Nonnull
    public Collection<File> findImages(@Nonnull final String prefix, @Nonnull final ChannelMap channelMap) {
        // Find all sets for a given prefix
        final Map<Integer, Set<File>> imagesByTime = findAllImagesByPrefix(prefix);
        LOG.info("Found {} sets of images with the same prefix '{}'", imagesByTime.size(), prefix);

        // Pick one set of images
        final Set<File> images =
                imagesByTime.entrySet().stream()
                        .findFirst()
                        .map(Map.Entry::getValue)
                        .orElseGet(Collections::emptySet);

        if (images.isEmpty()) {
            throw new ImageNotFoundException("No images found for the prefix: '%s'", prefix);
        }

        // Find images for a given channels
        return channelMap.channels()
                .stream()
                .map(c -> {
                    final File file = getFileForChannel(images, c);
                    if (file == null) {
                        throw new ImageNotFoundException(
                                "No images found for the prefix '%s' and channel '%d'", prefix, c);
                    }
                    return file;
                }).collect(Collectors.toSet());
    }

    private Map<Integer, Set<File>> findAllImagesByPrefix(@Nonnull final String prefix) {
        try (final Stream<Path> stream = Files.list(dataDirectory)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> {
                        final String filename = toFilename(p);
                        return filename.startsWith(prefix) && filename.endsWith(".tif");
                    })
                    .map(Path::toFile)
                    .collect(Collectors.groupingBy(new ImageFileClassifier(prefix), TreeMap::new, Collectors.toSet()));
        } catch (IOException e) {
            throw new UncheckedIOException("Exception while searching files", e);
        }
    }

    private static String toFilename(final Path path) {
        return path.getName(path.getNameCount() - 1).toString();
    }

    private static File getFileForChannel(final Set<File> images, final int channel) {
        final String suffix = String.format("B%02d.tif", channel);
        return images.stream().filter(i -> i.getName().endsWith(suffix)).findFirst().orElse(null);
    }

}
