package krystiannowak.webserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        InputStream is = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        ResponseWriter writer = new ResponseWriter(out);
        RequestParser parser = new AntlrRequestParser();

        RequestDispatcher dispatcher = new RequestDispatcher();
        dispatcher.setHandler(FilesystemGetRequestHandler.METHOD,
                new FilesystemGetRequestHandler(documentRoot));

        return parser.parse(is).flatMap(request -> {
            Optional<Response> responseOpt = dispatcher.handle(request);
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
