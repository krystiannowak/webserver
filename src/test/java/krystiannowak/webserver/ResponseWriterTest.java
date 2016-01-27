package krystiannowak.webserver;

import static krystiannowak.webserver.SimpleStringSerialization.deserialize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

public class ResponseWriterTest {

    @Test
    public void dummyResponseIsProperlyWritten() throws IOException {

        StringResponse response = new StringResponse(HttpURLConnection.HTTP_OK,
                "OK", "Hello world");

        response.setHeaders(ImmutableMap.<String, String> builder()
                .put("Date", "Sun, 24 Jan 2016 00:02:08 GMT")
                .put("Server", "Apache/2.2.16 (Debian)")
                .put("Last-Modified", "Sun, 14 Oct 2012 21:45:51 GMT")
                .put("Keep-Alive", "timeout=15, max=100")
                .put("Connection", "Keep-Alive")
                .put("Content-Type", "text/plain").build());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ResponseWriter sut = new ResponseWriter(baos);
        sut.write(response);

        byte[] written = baos.toByteArray();
        sut.close();

        assertTrue("written message should not be empty", written.length > 0);

        assertEquals("written message should match the example",
                deserialize(readExample()), deserialize(written));

    }

    private byte[] readExample() throws IOException {
        return Resources.toByteArray(
                getClass().getResource("/Apache-200OK-response-example.txt"));
    }
}
