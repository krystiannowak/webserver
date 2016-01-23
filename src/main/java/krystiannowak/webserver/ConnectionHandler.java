package krystiannowak.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import rx.Observable;

/**
 * A main handler that deals with single HTTP connection.
 *
 * @author krystiannowak
 *
 */
public class ConnectionHandler {

    /**
     * The main method of the handler to deal with the HTTP connection given.
     *
     * @param connection the HTTP connection
     * @return an Observable of emitted Messages
     */
    public final Observable<Message> handle(final HttpConnection connection) {

        InputStream is = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        ResponseWriter writer = new ResponseWriter(out);

        RequestParser parser = new RequestParser() {

            @Override
            public Observable<Request> parse(final InputStream is) {
                return Observable.just(new Request());
            }

        };

        RequestHandler handler = new RequestHandler() {
            @Override
            public Optional<Response> handle(final Request request) {
                return Optional.empty();
            }
        };

        return parser.parse(is).flatMap(request -> {
            Optional<Response> response = handler.handle(request);
            if (response.isPresent()) {
                try {
                    writer.write(response.get());
                    return Observable
                            .from(new Message[] {request, response.get() });
                } catch (IOException e) {
                    return Observable.error(e);
                }
            } else {
                try {
                    writer.close();
                    out.close();
                    is.close();
                    connection.close();
                    return Observable.just(request);
                } catch (IOException e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
