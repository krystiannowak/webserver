package krystiannowak.webserver;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Writes {@link Response}s to an {@link OutputStream}.
 *
 * @author krystiannowak
 *
 */
public class ResponseWriter implements Closeable {

    /**
     * An {@link OutputStream} to write {@link Response}s to.
     */
    private final OutputStream os;

    /**
     * Creates the writer.
     *
     * @param outputStream
     *            the {@link OutputStream} for {@link Response}s
     */
    public ResponseWriter(final OutputStream outputStream) {
        this.os = outputStream;
    }

    /**
     * Writes the {@link Response} given to the {@link OutputStream}.
     *
     * @param response
     *            the {@link Response} to write
     * @throws IOException
     *             if an I/O error occurs
     */
    public final void write(final Response response) throws IOException {
        String stringRepresentation = response.toString();
        byte[] data = stringRepresentation
                .getBytes(StandardCharsets.ISO_8859_1);
        os.write(data);
    }

    @Override
    public final void close() throws IOException {
        if (os != null) {
            os.close();
        }
    }
}
