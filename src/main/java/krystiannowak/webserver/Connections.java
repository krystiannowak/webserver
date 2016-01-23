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
     * Creates an Observable of HttpConnection out of the given socket.
     *
     * @param socket
     *            the socket to create connection for.
     * @return the observable of connection
     */
    public static Observable<HttpConnection> connection(final Socket socket) {
        try {
            return Observable.just(new HttpConnection(socket));
        } catch (IOException e) {
            return Observable.error(e);
        }

    }

    /**
     * Handles given connection and emits messages of this handling.
     *
     * @param connection
     *            the connection to handle
     * @return the observable of messages emitted during handling the connection
     */
    public static Observable<Message> handle(final HttpConnection connection) {
        return new ConnectionHandler().handle(connection);
    }
}
