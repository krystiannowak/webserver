package krystiannowak.webserver;

import static krystiannowak.webserver.SimpleStringSerialization.serialize;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import com.google.common.primitives.Bytes;

/**
 * Writes {@link Response}s to an {@link OutputStream}.
 *
 * @author krystiannowak
 *
 */
public class ResponseWriter implements Closeable {

    /**
     * SP space representation as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-2.2">https://tools.
     * ietf.org/html/rfc2616#section-2.2</a>.
     */
    private static final String SP = " ";

    /**
     * CRLF carriage return and linefeed representation as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-2.2">https://tools.
     * ietf.org/html/rfc2616#section-2.2</a>.
     */
    private static final String CRLF = "\r\n";

    /**
     * An {@link OutputStream} to write {@link Response}s to.
     */
    private final OutputStream os;

    /**
     * Creates the writer.
     *
     * @param outputStream
     *            the {@link OutputStream} for {@link Response}s
     */
    public ResponseWriter(final OutputStream outputStream) {
        this.os = outputStream;
    }

    /**
     * Writes the {@link Response} given to the {@link OutputStream}.
     *
     * @param response
     *            the {@link Response} to write
     * @throws IOException
     *             if an I/O error occurs
     */
    public final void write(final Response response) throws IOException {
        os.write(Bytes.concat(
                serialize(new ResponseTextBuilder(response).toString()),
                response.getMessageBody()));
        os.flush();
    }

    @Override
    public final void close() throws IOException {
        if (os != null) {
            os.close();
        }
    }

    /**
     * A builder to write the textual part of the response as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-6">https://tools.
     * ietf.org/html/rfc2616#section-6</a>.
     *
     * @author krystiannowak
     *
     */
    private static final class ResponseTextBuilder {

        /**
         * A {@link StringBuilder} to accumulate the text to be build.
         */
        private final StringBuilder sb = new StringBuilder();

        /**
         * Constructs the builder.
         *
         * @param response
         *            the response for which to build the textual representation
         */
        private ResponseTextBuilder(final Response response) {
            appendStatusLine(response);
            appendHeaderLines(response);
            sb.append(CRLF);
        }

        @Override
        public String toString() {
            return sb.toString();
        }

        /**
         * Appends the HTTP response Status-Line as per
         * <a href="https://tools.ietf.org/html/rfc2616#section-6.1">https://
         * tools.ietf.org/html/rfc2616#section-6.1</a>.
         *
         * @param response
         *            the response to build the Status-Line for
         */
        private void appendStatusLine(final Response response) {
            sb.append(response.getHttpVersion());
            sb.append(SP);
            sb.append(response.getStatusCode());
            sb.append(SP);
            sb.append(response.getReasonPhrase());
            sb.append(CRLF);
        }

        /**
         * Builds HTTP request header lines as per
         * <a href="https://tools.ietf.org/html/rfc2616#section-6">https://tools
         * .ietf.org/html/rfc2616#section-6</a> additionally adding
         * <code>Content-Length</code> HTTP header with a value of content
         * length if present.
         *
         * @param response
         *            the response to build header lines for
         */
        private void appendHeaderLines(final Response response) {
            response.getHeaders()
                    .forEach((name, value) -> appendHeader(name, value));
            response.getContentLength()
                    .ifPresent(contentLength -> appendHeader("Content-Length",
                            contentLength));
        }

        /**
         * Appends an HTTP header line as per
         * <a href="https://tools.ietf.org/html/rfc2616#section-6">https://tools
         * .ietf.org/html/rfc2616#section-6</a>.
         *
         * @param <T>
         *            the type of the header value
         * @param name
         *            the header name
         * @param value
         *            the header value
         */
        private <T> void appendHeader(final String name, final T value) {
            sb.append(name);
            sb.append(':');
            sb.append(SP);
            sb.append(value);
            sb.append(CRLF);
        }

    }
}
