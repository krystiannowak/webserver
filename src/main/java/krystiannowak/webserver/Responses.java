package krystiannowak.webserver;

import java.net.HttpURLConnection;

/**
 * Utilities for creating most common {@link Response}s.
 *
 * @author krystiannowak
 *
 */
public final class Responses {

    /**
     * No instantiation possible.
     */
    private Responses() {
    }

    /**
     * 400 Bad Request as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-10.4.1">https://
     * tools.ietf.org/html/rfc2616#section-10.4.1</a>.
     *
     * @param message
     *            the message to send
     * @return 400 Bad Request
     */
    public static Response badRequest(final String message) {
        return new StringResponse(HttpURLConnection.HTTP_BAD_REQUEST,
                "Bad Request", message);
    }

    /**
     * 403 Forbidden as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-10.4.4">https://
     * tools.ietf.org/html/rfc2616#section-10.4.4</a>.
     *
     * @param requestUri
     *            current Request-URI
     * @return 403 Forbidden {@link Response}
     */
    public static Response forbidden(final String requestUri) {
        return new StringResponse(HttpURLConnection.HTTP_FORBIDDEN, "Forbidden",
                "Access to resource '" + requestUri + "' is forbidden");
    }

    /**
     * 404 Not Found as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-10.4.5">https://
     * tools.ietf.org/html/rfc2616#section-10.4.5</a>.
     *
     * @param requestUri
     *            current Request-URI
     * @return 404 Not Found {@link Response}
     */
    public static Response notFound(final String requestUri) {
        return new StringResponse(HttpURLConnection.HTTP_NOT_FOUND, "Not Found",
                "Resource '" + requestUri + "' not found");
    }

    /**
     * 405 Method Not Allowed as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-10.4.6">https://
     * tools.ietf.org/html/rfc2616#section-10.4.6</a>.
     *
     * @param method
     *            current HTTP method
     * @return 405 Method Not Allowed {@link Response}
     */
    public static Response badMethod(final String method) {
        return new StringResponse(HttpURLConnection.HTTP_BAD_METHOD,
                "Method Not Allowed", "Method '" + method + "' is not allowed");
    }

    /**
     * 505 HTTP Version Not Supported as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-10.5.6">https://
     * tools.ietf.org/html/rfc2616#section-10.5.6</a>.
     *
     * @param httpVersion
     *            current HTTP-Version
     * @return 505 HTTP Version Not Supported {@link Response}
     */
    public static Response version(final String httpVersion) {
        return new StringResponse(HttpURLConnection.HTTP_VERSION,
                "HTTP Version Not Supported", "The HTTP version '" + httpVersion
                        + "' provided in the quest is not supported");
    }
}
