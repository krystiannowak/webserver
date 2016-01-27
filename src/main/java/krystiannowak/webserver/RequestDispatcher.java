package krystiannowak.webserver;

import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_VERSION;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

/**
 * A {@link RequestHandler} being in fact a dispatcher for registered
 * {@link RequestHandler}s per given HTTP method.
 *
 * @author krystiannowak
 *
 */
public class RequestDispatcher implements RequestHandler {

    /**
     * Resistered {@link RequestHandler}s per HTTP method.
     */
    private final Map<String, RequestHandler> methodToHandler = Maps
            .newHashMap();

    /**
     * Setting given {@link RequestHandler} per HTTP method.
     *
     * @param method
     *            the HTTP method to be handled
     * @param handler
     *            the {@link RequestHandler} registered
     */
    public final void setHandler(final String method,
            final RequestHandler handler) {
        methodToHandler.put(method, handler);
    }

    @Override
    public final Optional<Response> handle(final Request request) {

        if (request.getHttpVersion() == null
                || request.getHttpVersion().equals("HTTP/1.0")
                || !request.getHttpVersion().startsWith("HTTP/1.")) {

            return stringResponse(HTTP_VERSION, "HTTP Version Not Supported",
                    "The HTTP version provided in the quest is not supported: "
                            + request.getHttpVersion());

        }

        if (request.getMethod() == null || request.getMethod().isEmpty()) {
            return stringResponse(HTTP_BAD_REQUEST, "Bad Request",
                    "No method is set");

        }

        RequestHandler methodHandler = methodToHandler.get(request.getMethod());
        if (methodHandler == null) {
            return stringResponse(HTTP_BAD_METHOD, "Method Not Allowed",
                    "Method is not allowed: " + request.getMethod());
        }

        return methodHandler.handle(request);
    }

    /**
     * A handy tool for short {@link StringResponse} creation.
     *
     * @param statusCode
     *            the HTTP status code
     * @param reasonPhrase
     *            the HTTP reason phrase for the code given
     * @param messageBody
     *            the textual message body
     * @return {@link Optional} of {@link StringResponse} created.
     */
    private Optional<Response> stringResponse(final int statusCode,
            final String reasonPhrase, final String messageBody) {
        return Optional
                .of(new StringResponse(statusCode, reasonPhrase, messageBody));
    }

}
