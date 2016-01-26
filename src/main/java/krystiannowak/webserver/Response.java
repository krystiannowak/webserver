package krystiannowak.webserver;

/**
 * A representation of an HTTP {@link Response}.
 *
 * @author krystiannowak
 *
 */
public class Response implements Message {

    @Override
    public final String toString() {
        return "HTTP/1.1 200 OK\r\n" + "Date: Sun, 24 Jan 2016 00:02:08 GMT\r\n"
                + "Server: Apache/2.2.16 (Debian)\r\n"
                + "Last-Modified: Sun, 14 Oct 2012 21:45:51 GMT\r\n"
                + "Content-Length: 11\r\n"
                + "Keep-Alive: timeout=15, max=100\r\n"
                + "Connection: Keep-Alive\r\n" + "Content-Type: text/plain\r\n"
                + "\r\n" + "Hello world";
    }

}
