package krystiannowak.webserver;

import static krystiannowak.webserver.SimpleStringSerialization.serialize;

/**
 * A class to support {@link Response}s which body is just a simple
 * {@link String}.
 *
 * @author krystiannowak
 *
 */
public class StringResponse extends DefaultResponse {

    /**
     * Creates a {@link StringResponse} instance.
     *
     * @param statusCode
     *            the HTTP status code
     * @param reasonPhrase
     *            the HTTP reason phrase for the code given
     * @param messageBody
     *            the textual message body
     */
    protected StringResponse(final int statusCode, final String reasonPhrase,
            final String messageBody) {
        super(statusCode, reasonPhrase);
        setMessageBody(serialize(messageBody));
    }
}
