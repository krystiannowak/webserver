package krystiannowak.webserver;

import static java.net.HttpURLConnection.HTTP_OK;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import com.google.common.net.HttpHeaders;

/**
 * Naive {@link Response} for transmitting {@link File}'s content. Note that
 * this simple implementation reads all the content of the {@link File} into
 * byte array in memory - be careful with big files.
 *
 * @author krystiannowak
 *
 */
public class FileResponse extends DefaultResponse {

    /**
     * Creating an instance of {@link FileResponse} based on given {@link File}.
     *
     * @param file
     *            the {@link File} for which this {@link Response} is to be
     *            created.
     */
    public FileResponse(final File file) {
        super(HTTP_OK, "OK");
        putHeader(HttpHeaders.CONTENT_TYPE,
                MimeTypeUtils.guessContentType(file));
        readFileIntoMessageBody(file);
    }

    /**
     * Reads the content of the {@link File} given into memory.
     *
     * @param file
     *            the {@link File} to read
     */
    private void readFileIntoMessageBody(final File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ByteStreams.copy(fis, baos);
            setMessageBody(baos.toByteArray());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
