package io.interstellar.image.exception;

public final class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(final String message, final Object... args) {
        super(String.format(message, args));
    }

}
