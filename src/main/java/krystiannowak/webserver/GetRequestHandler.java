package krystiannowak.webserver;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.Optional;

/**
 * A {@link RequestDispatcher} for HTTP method GET.
 *
 * @author krystiannowak
 *
 */
public class GetRequestHandler implements RequestHandler {

    /**
     * Method GET {@link String} value.
     */
    public static final String METHOD = "GET";

    @Override
    public final Optional<Response> handle(final Request request) {
        return Optional.of(new StringResponse(HTTP_OK, "OK", "Hello world"));
    }
}
