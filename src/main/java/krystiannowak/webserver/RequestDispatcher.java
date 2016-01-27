package krystiannowak.webserver;

import static krystiannowak.webserver.Responses.badMethod;
import static krystiannowak.webserver.Responses.badRequest;
import static krystiannowak.webserver.Responses.version;

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

            return Optional.of(version(request.getHttpVersion()));
        }

        if (request.getMethod() == null || request.getMethod().isEmpty()) {
            return Optional.of(badRequest("No method is set"));

        }

        RequestHandler methodHandler = methodToHandler.get(request.getMethod());
        if (methodHandler == null) {
            return Optional.of(badMethod(request.getMethod()));
        }

        return methodHandler.handle(request);
    }

}
