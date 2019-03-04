package io.interstellar.image.exception;

public final class GDALInvocationException extends RuntimeException {

    public GDALInvocationException(final String command, final int errorCode) {
        super(String.format("%s command returned non zero exit code: %d", command, errorCode));
    }

    public GDALInvocationException(final String command, final Throwable cause) {
        super(String.format("Exception occurred while calling: %s", command), cause);
    }

}
