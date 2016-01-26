package krystiannowak.webserver;

import static krystiannowak.webserver.Observables.get;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AntlrRequestParserTest {

    private RequestParser sut = new AntlrRequestParser();

    @Test
    public void curlGetRequestIsParsed() throws Exception {

        Request request = parseRequestFromResource(
                "/curl-GET-request-example.txt");

        assertEquals("Request method should match", "GET", request.getMethod());

        assertEquals("Request URI should match", "/", request.getRequestUri());

        assertEquals("HTTP version should match", "HTTP/1.1",
                request.getHttpVersion());

        assertEquals("Host header should match", "127.0.0.1:31337",
                request.getHost());

        assertNotNull("User-Agent header should be set",
                request.getUserAgent());
        assertTrue("User-Agent header should start with curl",
                request.getUserAgent().startsWith("curl"));

        assertEquals("Accept header should match", "*/*", request.getAccept());

        assertNull("Accept-Language header should not be set",
                request.getAcceptLanguage());

        assertNull("Accept-Encoding header should not be set",
                request.getAcceptEncoding());

        assertNull("Connection header should not be set",
                request.getConnection());
    }

    @Test
    public void firefoxGetRequestIsParsed() throws Exception {
        Request request = parseRequestFromResource(
                "/Firefox-GET-request-example.txt");

        assertEquals("Request method should match", "GET", request.getMethod());

        assertEquals("Request URI should match", "/", request.getRequestUri());

        assertEquals("HTTP version should match", "HTTP/1.1",
                request.getHttpVersion());

        assertEquals("Host header should match", "127.0.0.1:31337",
                request.getHost());

        assertNotNull("User-Agent header should be set",
                request.getUserAgent());
        assertTrue("User-Agent header should start with Mozilla",
                request.getUserAgent().startsWith("Mozilla"));
        assertTrue("User-Agent header should contain Firefox",
                request.getUserAgent().contains("Firefox"));

        assertNotNull("Accept header should be set", request.getAccept());
        assertTrue("Accept header should match",
                request.getAccept().contains("*/*"));

        assertNotNull("Accept-Language header should be set",
                request.getAcceptLanguage());
        assertTrue("Accept-Language header should match",
                request.getAcceptLanguage().contains("en-US"));

        assertNotNull("Accept-Encoding header should be set",
                request.getAcceptEncoding());
        assertTrue("Accept-Encoding header should match",
                request.getAcceptEncoding().contains("deflate"));

        assertEquals("Connection header should match", "keep-alive",
                request.getConnection());

    }

    private Request parseRequestFromResource(String resourceName)
            throws Exception {
        Request request = get(
                sut.parse(getClass().getResourceAsStream(resourceName)));
        assertNotNull("request should exist", request);

        return request;
    }

}
