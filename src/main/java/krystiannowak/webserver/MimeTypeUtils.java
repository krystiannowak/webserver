package krystiannowak.webserver;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.net.MediaType;

/**
 * A tool to support mime types for certain files.
 *
 * @author krystiannowak
 *
 */
public final class MimeTypeUtils {

    /**
     * A static logger.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(MimeTypeUtils.class);

    /**
     * A fallback mime type in case no other guess is possible.
     */
    private static final MediaType FALLBACK_MIME_TYPE = MediaType.OCTET_STREAM;

    /**
     * No instantiation possible.
     */
    private MimeTypeUtils() {
    };

    /**
     * Guessing mime type based on given {@link File}'s properties.
     *
     * @param file
     *            file to guess mime type for
     * @return guessed mime type
     */
    public static String guessContentType(final File file) {

        String guess = URLConnection.guessContentTypeFromName(file.getName());
        if (!Strings.isNullOrEmpty(guess)) {
            return guess;
        }

        try {
            guess = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }

        if (!Strings.isNullOrEmpty(guess)) {
            return guess;
        }

        return FALLBACK_MIME_TYPE.toString();

    }

}
