package krystiannowak.webserver;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import com.google.common.base.Charsets;

/**
 * Writes responses to output stream.
 *
 * @author krystiannowak
 *
 */
public class ResponseWriter implements Closeable {

    /**
     * An output stream to write responses to.
     */
    private final OutputStream os;

    /**
     * Creates the writer.
     *
     * @param outputStream
     *            the output stream for responses
     */
    public ResponseWriter(final OutputStream outputStream) {
        this.os = outputStream;
    }

    /**
     * Writes the response given to the output stream.
     *
     * @param response
     *            the response to write
     * @throws IOException
     *             if an I/O error occurs
     */
    public final void write(final Response response) throws IOException {
        String stringRepresentation = response.toString();
        byte[] data = stringRepresentation.getBytes(Charsets.UTF_8);
        os.write(data);
    }

    @Override
    public final void close() throws IOException {
        if (os != null) {
            os.close();
        }
    }
}
