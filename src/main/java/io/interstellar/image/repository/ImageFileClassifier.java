package io.interstellar.image.repository;

import java.io.File;
import java.util.function.Function;

/**
 * Package level visibility, because it's not a general purpose component.
 */
class ImageFileClassifier implements Function<File, Integer> {

    private static final int TIME_COMPONENT_LENGTH = 6;

    private final int prefixLength;

    ImageFileClassifier(final String prefix) {
        this.prefixLength = prefix.length();
    }

    @Override
    public Integer apply(final File file) {
        return Integer.valueOf(file.getName().substring(prefixLength, prefixLength + TIME_COMPONENT_LENGTH));
    }

}
