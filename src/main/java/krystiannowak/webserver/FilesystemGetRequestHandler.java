package krystiannowak.webserver;

import static java.net.HttpURLConnection.HTTP_OK;
import static krystiannowak.webserver.Responses.forbidden;
import static krystiannowak.webserver.Responses.notFound;

import java.io.File;
import java.net.URI;
import java.util.Optional;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

/**
 * A {@link RequestDispatcher} for HTTP method GET.
 *
 * @author krystiannowak
 *
 */
public class FilesystemGetRequestHandler implements RequestHandler {

    /**
     * The document root to look files and directories for.
     */
    private final File documentRoot;

    /**
     * Instantiates this {@link RequestHandler}.
     *
     * @param documentRoot
     *            the document root to look files and directories for
     */
    public FilesystemGetRequestHandler(final File documentRoot) {
        this.documentRoot = documentRoot;
    }

    /**
     * Method GET {@link String} value.
     */
    public static final String METHOD = "GET";

    @Override
    public final Optional<Response> handle(final Request request) {

        String requestUri = request.getRequestUri();

        if (!requestUri.startsWith("/")) {
            return Optional.of(forbidden(requestUri));
        }

        URI uri = URI.create(requestUri);

        String path = uri.getPath();

        File resource = new File(documentRoot, path).getAbsoluteFile();

        if (!resource.exists() || resource.isHidden()) {
            return Optional.of(notFound(path));
        }

        if (!resource.canRead()) {
            return Optional.of(forbidden(path));
        }

        if (resource.isFile()) {
            return Optional.of(new FileResponse(resource));
        }

        if (resource.isDirectory()) {
            IndexHtmlGenerator indexGenerator = new IndexHtmlGenerator(
                    documentRoot, path, Optional.empty());
            DefaultResponse response = new StringResponse(HTTP_OK, "OK",
                    indexGenerator.getHtml());
            response.putHeader(HttpHeaders.CONTENT_TYPE,
                    MediaType.HTML_UTF_8.toString());
            return Optional.of(response);
        }

        return Optional.of(forbidden(path));
    }
}
