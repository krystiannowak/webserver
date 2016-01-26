package krystiannowak.webserver;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * A utility to handle {@link Observable}s in test assertions.
 *
 * @author krystiannowak
 *
 */
public final class Observables {

    /**
     * No instantiation possible.
     */
    private Observables() {
    }

    /**
     * Waits for an {@link Observable} for some maximum time and gets its data.
     *
     * @param o
     *            an {@link Observable} to wait for and get data from
     * @return the data from the {@link Observable} given
     * @throws Exception
     *             thrown if any {@link Exception} occurs during acquiring the
     *             data from the {@link Observable}
     */
    public static <T> T get(Observable<T> o) throws Exception {
        return o.toBlocking().toFuture().get(5, TimeUnit.MINUTES);
    }

}
