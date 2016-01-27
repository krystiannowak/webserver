package krystiannowak.webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import rx.Observable;

/**
 * A main handler that deals with single HTTP connection.
 *
 * @author krystiannowak
 *
 */
public class ConnectionHandler {

    /**
     * The document root to look files and directories for.
     */
    private final File documentRoot;

    /**
     * Instantiates this handler.
     *
     * @param documentRoot
     *            the document root to look files and directories for
     */
    public ConnectionHandler(final File documentRoot) {
        this.documentRoot = documentRoot;
    }

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

        log.info("handling connection");

        InputStream is = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        ResponseWriter writer = new ResponseWriter(out);
        RequestParser parser = new AntlrRequestParser();

        RequestDispatcher dispatcher = new RequestDispatcher();
        dispatcher.setHandler(FilesystemGetRequestHandler.METHOD,
                new FilesystemGetRequestHandler(documentRoot));

        return parser.parse(is).flatMap(request -> {
            log.info("on next request parsed");
            Optional<Response> responseOpt = dispatcher.handle(request);
            if (responseOpt.isPresent()) {
                Response response = responseOpt.get();
                log.info("about to write a response");
                try {
                    writer.write(response);
                    return Observable.from(new Message[] {request, response});
                } catch (IOException e) {
                    return Observable.error(e);
                }
            } else {
                return Observable.empty();
            }
        }).doOnCompleted(() -> {
            try {
                log.info("closing connection");
                writer.close();
                out.close();
                is.close();
                connection.close();
            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        });
    }
}
