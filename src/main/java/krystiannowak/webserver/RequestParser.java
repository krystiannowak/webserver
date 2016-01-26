package krystiannowak.webserver;

import java.io.InputStream;

import rx.Observable;

/**
 * A parser to create {@link Request}s out of an {@link InputStream}.
 *
 * @author krystiannowak
 *
 */
public interface RequestParser {

    /**
     * Generates {@link Request} objects from the {@link InputStream} given.
     *
     * @param is
     *            the {@link InputStream}
     * @return an {@link Observable} emitting parsed {@link Request}s
     */
    Observable<Request> parse(InputStream is);

}
