package krystiannowak.webserver;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;

/**
 * Default {@link Response} implementation.
 *
 * @author krystiannowak
 *
 */
public class DefaultResponse implements Response {

    /**
     * For simplicify assuming HTTP v1.1 only.
     */
    private static final String HTTP_VERSION = "HTTP/1.1";

    /**
     * No message body by default.
     */
    private static final byte[] EMPTY_MESSAGE_BODY = new byte[0];

    /**
     * The HTTP status code.
     */
    private final int statusCode;

    /**
     * The HTTP reason phrase for given status.
     */
    private final String reasonPhrase;

    /**
     * HTTP headers. Note the simplification - only unique headers supported.
     */
    private Map<String, String> headers = Maps.newHashMap();

    /**
     * Message body binary.
     */
    private byte[] messageBody = EMPTY_MESSAGE_BODY;

    /**
     * Creates {@link DefaultResponse} instance.
     *
     * @param statusCode
     *            HTTP status code
     * @param reasonPhrase
     *            HTTP reason phrase for the status code
     */
    protected DefaultResponse(final int statusCode, final String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public final String getHttpVersion() {
        return HTTP_VERSION;
    }

    @Override
    public final int getStatusCode() {
        return statusCode;
    }

    @Override
    public final String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public final Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Setting HTTP headers. Will overwrite any previously headers set.s
     *
     * @param headers
     *            the HTTP headers to set.
     */
    public final void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public final byte[] getMessageBody() {
        return Bytes.concat(messageBody);
    }

    /**
     * Sets the message body.
     *
     * @param messageBody
     *            the message body to be set
     */
    public final void setMessageBody(final byte[] messageBody) {
        this.messageBody = Bytes.concat(messageBody);
    }

    @Override
    public final Optional<Integer> getContentLength() {
        if (messageBody.length > 0) {
            return Optional.of(messageBody.length);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public final String toString() {
        return "DefaultResponse [statusCode=" + statusCode + ", reasonPhrase="
                + reasonPhrase + ", headers=" + headers + ", messageBody="
                + Arrays.toString(messageBody) + "]";
    }

}
