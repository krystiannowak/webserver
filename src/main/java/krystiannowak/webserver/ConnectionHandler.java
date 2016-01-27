package krystiannowak.webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * A main handler that deals with single HTTP connection.
 *
 * @author krystiannowak
 *
 */
public class ConnectionHandler {

    /**
     * Instance logger.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * The main method of the handler to deal with the HTTP connection given.
     *
     * @param connection
     *            the HTTP connection
     * @return an Observable of emitted Messages
     */
    public final Observable<Message> handle(final HttpConnection connection) {

        InputStream is = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        ResponseWriter writer = new ResponseWriter(out);
        RequestParser parser = new AntlrRequestParser();

        RequestHandler handler = new RequestHandler() {
            @Override
            public Optional<Response> handle(final Request request) {
                return Optional.of(new StringResponse(HttpURLConnection.HTTP_OK,
                        "OK", "Hello world"));
            }
        };

        return parser.parse(is).flatMap(request -> {
            Optional<Response> responseOpt = handler.handle(request);
            if (responseOpt.isPresent()) {
                try {
                    Response response = responseOpt.get();
                    log.info("about to write a response");
                    writer.write(response);
                    return Observable.from(new Message[] {request, response});
                } catch (IOException e) {
                    return Observable.error(e);
                }
            } else {
                log.info("closing the connection");
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
