package krystiannowak.webserver;

import java.util.Optional;

/**
 * Main handler to deal with requests and (if applicable) generate responses.
 *
 * @author krystiannowak
 *
 */
public interface RequestHandler {

    /**
     * Handles given request and (if applicable) generates a response
     * accordingly. In case the response is not generated this should normally
     * mean the communication is to be terminated.
     *
     * @param request
     *            a request to be handled
     * @return an optional response
     */
    Optional<Response> handle(Request request);

}
