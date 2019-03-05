package io.interstellar.image.service;

import io.interstellar.image.exception.GDALInvocationException;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class GDALInvoker {

    private static final String GDALBUILDVRT_COMMAND = "gdalbuildvrt";

    private static final String GDAL_TRANSLATE_COMMAND = "gdal_translate";

    private static final String VRT_FILE_SUFFIX = ".vrt";

    private static final String JPG_FILE_SUFFIX = ".jpg";

    private static final int SUCCESS = 0;

    /**
     * Wrapper around <a href="https://www.gdal.org/gdalbuildvrt.html">gdalbuildvrt</a>.
     *
     * @param redChannel   red sensor band file
     * @param greenChannel green sensor band file
     * @param blueChannel  blue sensor band file
     * @return generated {@code .vrt} file
     */
    public File mergeChannels(
            @Nonnull final File redChannel, @Nonnull final File greenChannel, @Nonnull final File blueChannel) {
        final File vrtFile = uncheckedCreateTempFile(VRT_FILE_SUFFIX);

        invoke(GDALBUILDVRT_COMMAND,
                vrtFile.getAbsolutePath(),
                "-separate",
                redChannel.getAbsolutePath(),
                greenChannel.getAbsolutePath(),
                blueChannel.getAbsolutePath());

        return vrtFile;
    }

    private void invoke(final String command, final String... args) {
        final List<String> arguments = Arrays.asList(args);
        final List<String> commandWithArguments = new ArrayList<>(arguments.size() + 1);
        commandWithArguments.add(command);
        commandWithArguments.addAll(arguments);

        try {
            final ProcessResult result = new ProcessExecutor(commandWithArguments).execute();

            if (result.getExitValue() != SUCCESS) {
                throw new GDALInvocationException(command, result.getExitValue());
            }
        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new GDALInvocationException(command, e);
        }
    }

    /**
     * Wrapper around <a href="https://www.gdal.org/gdal_translate.html">gdal_translate</a>.
     *
     * @param vrtFile vrt file
     * @return generated {@code .jpg} file
     */
    public File generateImageFromVRT(@Nonnull final File vrtFile) {
        final File jpgFile = uncheckedCreateTempFile(JPG_FILE_SUFFIX);

        invoke(GDAL_TRANSLATE_COMMAND,
                "-of", "jpeg", "-ot", "byte",
                "-scale", "-exponent", "0.4",
                vrtFile.getAbsolutePath(), jpgFile.getAbsolutePath());

        return jpgFile;
    }

    /**
     * Same as {@link GDALInvoker#generateImageFromVRT(File)}, but for single channel GeoTIFF.
     *
     * @param channelFile single band file
     * @return generated {@code .jpg} file
     * @see io.interstellar.image.model.ChannelMap#WATER_VAPOR
     */
    public File generateImageFromSingleChannel(@Nonnull final File channelFile) {
        final File jpgFile = uncheckedCreateTempFile(JPG_FILE_SUFFIX);

        invoke(GDAL_TRANSLATE_COMMAND,
                "-of", "jpeg", "-ot", "byte",
                "-b", "1", "-b", "1", "-b", "1",
                "-scale_1", "0", "65000", "0", "0",
                "-scale_2", "0", "65000", "0", "0",
                "-scale_3", "-exponent_3", "0.4",
                channelFile.getAbsolutePath(), jpgFile.getAbsolutePath());

        return jpgFile;
    }

    private static File uncheckedCreateTempFile(final String suffix) {
        try {
            return File.createTempFile("tmp", suffix);
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("Unable to create %s file in a temp folder", suffix), e);
        }
    }

}
