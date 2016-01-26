package krystiannowak.webserver;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A representation of an HTTP connection abstraction.
 *
 * @author krystiannowak
 *
 */
public class HttpConnection implements Closeable {

    /**
     * A {@link Socket} to handle the connection on.
     */
    private final Socket socket;

    /**
     * An {@link InputStream} to read the HTTP requests from.
     */
    private final InputStream inputStream;

    /**
     * An {@link OutputStream} to write the HTTP responses to.
     */
    private final OutputStream outputStream;

    /**
     * Creates the connection.
     *
     * @param socket
     *            the {@link Socket} to create connection on
     * @throws IOException
     *             if an I/O error occurs when getting the {@link InputStream}
     *             or the {@link OutputStream} from the {@link Socket}
     */
    public HttpConnection(final Socket socket) throws IOException {
        this.socket = socket;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    /**
     * @return the {@link InputStream} of this connection
     */
    public final InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the {@link OutputStream} of this connection
     */
    public final OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public final void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }

        if (outputStream != null) {
            outputStream.close();
        }

        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

}
