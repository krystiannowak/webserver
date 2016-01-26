package krystiannowak.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * @author krystiannowak
 *
 */
public final class Server {

    /**
     * Static {@link Logger} for main class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    /**
     * Default number of threads (if not overridden from configuration).
     */
    private static final int DEFAULT_NUMBER_OF_THREADS = 8;

    /**
     * Default port number for the server (if not overridden from
     * configuration).
     */
    private static final int DEFAULT_PORT_NUMBER = 31337;

    /**
     * No instantiation possible.
     */
    private Server() {
    }

    /**
     * Main entry point to the server executable.
     *
     * @param args
     *            arguments (not used)
     */
    public static void main(final String[] args) {

        final int numberOfThreads = DEFAULT_NUMBER_OF_THREADS;

        ExecutorService threadPoolExecutorService = Executors
                .newFixedThreadPool(numberOfThreads);
        Scheduler scheduler = Schedulers.from(threadPoolExecutorService);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                threadPoolExecutorService.shutdownNow();
            }
        });

        Observable.<Socket>create(subscriber -> {
            try {
                final int port = DEFAULT_PORT_NUMBER;

                final ServerSocket serverSocket = new ServerSocket(port);
                subscriber.add(Subscriptions.create(() -> {
                    try {
                        LOG.info("closing socket on port {}", port);
                        serverSocket.close();
                    } catch (IOException e) {
                        throw Throwables.propagate(e);
                    }
                }));
                LOG.info("server on port {} started", port);
                while (!subscriber.isUnsubscribed()) {
                    try {
                        Socket socket = serverSocket.accept();
                        subscriber.onNext(socket);
                    } catch (SocketTimeoutException e) {
                        LOG.warn("timeout while accepting socket connection",
                                e);
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }).flatMap(socket -> {
            LOG.info("receiving connection on a socket {}", socket);
            return Connections.connection(socket);
        }).flatMap(connection -> {
            return Connections.handle(connection);
        }).observeOn(scheduler).subscribe(message -> {
            LOG.info(message.toString());
        }, t -> {
            LOG.error("an error occured", t);
        });

    }

}
