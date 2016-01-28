package krystiannowak.webserver;

/**
 * Main handler to deal with {@link Request}s and generate {@link Response}s.
 *
 * @author krystiannowak
 *
 */
public interface RequestHandler {

    /**
     * Handles given {@link Request} and generates a {@link Response}
     * accordingly.
     *
     * @param request
     *            a {@link Request} to be handled
     * @return a {@link Response}
     */
    Response handle(Request request);

}
