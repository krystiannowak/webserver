package krystiannowak.webserver;

import java.io.File;
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
     * The default document root to look files and directories for.
     */
    public static final String DEFAULT_DOCUMENT_ROOT = "www";

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
    public static final int DEFAULT_PORT_NUMBER = 31337;

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

        final File documentRoot = new File(DEFAULT_DOCUMENT_ROOT)
                .getAbsoluteFile();
        assertDocumentRoot(documentRoot);

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

                LOG.info("new server subscriber");

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
                        LOG.info("new socket connection accepted");
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
            return Connections.handle(connection, documentRoot);
        }).observeOn(scheduler).subscribe(message -> {
            LOG.info(message.toString());
        }, t -> {
            LOG.error("an error occured", t);
        });

    }

    /**
     * Checks whether the document root exists and is generally usable.
     *
     * @param documentRoot
     *            the document root to check
     */
    private static void assertDocumentRoot(final File documentRoot) {
        if (!documentRoot.exists()) {
            exitWithDocumentRootError("docroot '{}' does not exist",
                    documentRoot);
        }

        if (!documentRoot.isDirectory()) {
            exitWithDocumentRootError("docroot '{}' is not a directory",
                    documentRoot);
        }

        if (!documentRoot.canRead()) {
            exitWithDocumentRootError("docroot '{}' is not readable",
                    documentRoot);
        }

    }

    /**
     * Exists the process logging error with document root context.
     *
     * @param message
     *            message to log with document root context
     * @param documentRoot
     *            document root context to use in the log
     */
    private static void exitWithDocumentRootError(final String message,
            final File documentRoot) {
        LOG.error(message, documentRoot);
        System.exit(1);
    }

}
