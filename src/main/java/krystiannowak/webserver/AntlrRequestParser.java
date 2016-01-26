package krystiannowak.webserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.primitives.Bytes;

import krystiannowak.webserver.gen.HttpRequestBaseListener;
import krystiannowak.webserver.gen.HttpRequestLexer;
import krystiannowak.webserver.gen.HttpRequestParser;
import krystiannowak.webserver.gen.HttpRequestParser.AcceptContext;
import krystiannowak.webserver.gen.HttpRequestParser.AcceptEncodingContext;
import krystiannowak.webserver.gen.HttpRequestParser.AcceptLanguageContext;
import krystiannowak.webserver.gen.HttpRequestParser.ConnectionContext;
import krystiannowak.webserver.gen.HttpRequestParser.HostContext;
import krystiannowak.webserver.gen.HttpRequestParser.HttpVersionContext;
import krystiannowak.webserver.gen.HttpRequestParser.MethodContext;
import krystiannowak.webserver.gen.HttpRequestParser.RequestURIContext;
import krystiannowak.webserver.gen.HttpRequestParser.UserAgentContext;
import rx.Observable;

/**
 * HTTP request parser based on ANTLR v4 grammar definition (simplified for this
 * exercise example).
 *
 * @author krystiannowak
 *
 */
public class AntlrRequestParser implements RequestParser {

    /**
     * An instance logger.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Extracts bytes from an {@link InputStream} given till the moment the data
     * from the stream is available. All other data which is not yet ready is
     * abandoned (possibly for next consumption from the stream, as the stream
     * is not closed after data extraction).
     *
     * @param is
     *            the input stream to extract bytes from
     * @return the extracted bytes of data
     * @throws IOException
     *             in case an I/O error occurs
     */
    private byte[] readAvailable(final InputStream is) throws IOException {

        byte[] result = new byte[0];
        int availableEstimate = 0;
        while ((availableEstimate = is.available()) > 0) {
            byte[] buffer = new byte[availableEstimate];
            int readLength = is.read(buffer, 0, buffer.length);
            result = Bytes.concat(result, Arrays.copyOf(buffer, readLength));
        }

        return result;
    }

    @Override
    public final Observable<Request> parse(final InputStream is) {

        try {
            byte[] buffer = readAvailable(is);

            log.info("buffering received data: {}",
                    new String(buffer, StandardCharsets.ISO_8859_1));

            CharStream charStream = new ANTLRInputStream(
                    new ByteArrayInputStream(buffer));
            HttpRequestLexer lexer = new HttpRequestLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HttpRequestParser parser = new HttpRequestParser(tokens);
            ParseTree tree = parser.request();
            ParseTreeWalker walker = new ParseTreeWalker();

            Request request = new Request();
            walker.walk(new HttpRequestBaseListener() {

                @Override
                public void enterMethod(final MethodContext ctx) {
                    request.setMethod(ctx.getText());
                }

                @Override
                public void enterRequestURI(final RequestURIContext ctx) {
                    request.setRequestUri(ctx.getText());
                }

                @Override
                public void enterHttpVersion(final HttpVersionContext ctx) {
                    request.setHttpVersion(ctx.getText());
                }

                @Override
                public void enterHost(final HostContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setHost(val));
                }

                @Override
                public void enterUserAgent(final UserAgentContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setUserAgent(val));
                }

                @Override
                public void enterAccept(final AcceptContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setAccept(val));
                }

                @Override
                public void enterAcceptLanguage(
                        final AcceptLanguageContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setAcceptLanguage(val));
                }

                @Override
                public void enterAcceptEncoding(
                        final AcceptEncodingContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setAcceptEncoding(val));
                }

                @Override
                public void enterConnection(final ConnectionContext ctx) {
                    extractHeaderValue(ctx)
                            .ifPresent(val -> request.setConnection(val));
                }

                private Optional<String> extractHeaderValue(
                        final ParseTree tree) {
                    if (tree.getChildCount() >= 2) {
                        return Optional.of(tree.getChild(1).getText().trim());
                    } else {
                        return Optional.empty();
                    }
                }

            }, tree);

            log.info("emitting parsed requst = {}", request);
            return Observable.just(request);
        } catch (IOException e) {
            return Observable.error(e);
        }
    }

}
