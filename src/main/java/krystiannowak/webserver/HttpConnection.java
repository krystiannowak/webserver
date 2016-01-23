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
     * The socket to handle the connection on.
     */
    private final Socket socket;

    /**
     * The input stream to read the HTTP requests from.
     */
    private final InputStream inputStream;

    /**
     * The output stream to write the HTTP responses to.
     */
    private final OutputStream outputStream;

    /**
     * Creates the connection.
     *
     * @param connectionSocket
     *            the socket to create connection on
     * @throws IOException
     *             if an I/O error occurs when getting the input or the output
     *             stream from the socket
     */
    public HttpConnection(final Socket connectionSocket) throws IOException {
        this.socket = connectionSocket;
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    /**
     * @return the input stream of this connection
     */
    public final InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @return the output stream of this connection
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
