package krystiannowak.webserver;

import java.util.Map;
import java.util.Optional;

/**
 * A representation of an HTTP {@link Response} (roughtly) based on
 * <a href="https://tools.ietf.org/html/rfc2616#section-6">https://tools.ietf.
 * org/html/rfc2616#section-6</a>.
 *
 * @author krystiannowak
 *
 */
public interface Response extends Message {

    /**
     * Gets an HTTP version.
     *
     * @return the HTTP version
     */
    String getHttpVersion();

    /**
     * Gets an HTTP status code.
     *
     * @return the HTTP status code
     */
    int getStatusCode();

    /**
     * Gets an HTTP reason phrase.
     *
     * @return the HTTP reason phrase
     */
    String getReasonPhrase();

    /**
     * Gets HTTP headers (simplified - unique, as given header can occurs only
     * once).
     *
     * @return HTTP headers
     */
    Map<String, String> getHeaders();

    /**
     * Gets message body. In case there is no body, an empty byte array is
     * returned.
     *
     * @return the message body
     */
    byte[] getMessageBody();

    /**
     * Gets the length of the non-empty content if present. If not, then either
     * the length cannot be calculated or the message body lenght is 0.
     *
     * @return an {@link Optional} of content length
     */
    Optional<Integer> getContentLength();

}
