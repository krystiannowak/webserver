package krystiannowak.webserver;

import java.util.Optional;

/**
 * Main handler to deal with {@link Request}s and (if applicable) generate
 * {@link Response}s.
 *
 * @author krystiannowak
 *
 */
public interface RequestHandler {

    /**
     * Handles given {@link Request} and (if applicable) generates a
     * {@link Response} accordingly. In case the {@link Response} is not
     * generated this should normally mean the communication is to be
     * terminated.
     *
     * @param request
     *            a {@link Request} to be handled
     * @return an {@link Optional} {@link Response}
     */
    Optional<Response> handle(Request request);

}
