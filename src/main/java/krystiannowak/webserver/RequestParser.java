package krystiannowak.webserver;

import java.io.InputStream;

import rx.Observable;

/**
 * A parser to create Requests out of an input stream.
 *
 * @author krystiannowak
 *
 */
public interface RequestParser {

    /**
     * Generates Request objects from the input stream given.
     *
     * @param is
     *            the input stream
     * @return an Observable emitting parsed Requests
     */
    Observable<Request> parse(InputStream is);

}
