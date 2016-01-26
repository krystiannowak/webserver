package krystiannowak.webserver;

import java.io.IOException;
import java.net.Socket;

import rx.Observable;

/**
 * A utility to handle connections.
 *
 * @author krystiannowak
 *
 */
public final class Connections {

    /**
     * No instantiation possible.
     */
    private Connections() {
    }

    /**
     * Creates an {@link Observable} of {@link HttpConnection} out of the given
     * socket.
     *
     * @param socket
     *            the socket to create connection for.
     * @return an {@link Observable} of {@link HTTPConnection}
     */
    public static Observable<HttpConnection> connection(final Socket socket) {
        try {
            return Observable.just(new HttpConnection(socket));
        } catch (IOException e) {
            return Observable.error(e);
        }

    }

    /**
     * Handles given connection and emits {@link Message}s of this handling.
     *
     * @param connection
     *            an {@link HTTPConnection} to handle
     * @return an {@link Observable} of {@link Message}s emitted during handling
     *         the {@link HTTPConnection}
     */
    public static Observable<Message> handle(final HttpConnection connection) {
        return new ConnectionHandler().handle(connection);
    }
}
