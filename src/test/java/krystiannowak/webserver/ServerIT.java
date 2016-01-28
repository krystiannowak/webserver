package krystiannowak.webserver;

import static krystiannowak.webserver.SimpleStringSerialization.deserialize;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.ByteStreams;

public class ServerIT {

    private ExecutorService executorService;

    @Before
    public void setUp() throws InterruptedException {
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> Server.main(new String[] {}));
        Thread.sleep(500);
    }

    @After
    public void tearDown() {
        executorService.shutdown();
    }

    @Test
    public void serverRespondsToHttpRequests() throws Exception {

        InputStream is = new URL(
                "http://localhost:" + Server.DEFAULT_PORT_NUMBER
                        + "/c/ca/caa/caaa/caaaa/caaaafile1").openStream();
        byte[] data = ByteStreams.toByteArray(is);
        is.close();

        assertEquals("response data needs to match",
                "this is just a dummy file", deserialize(data).trim());
    }

}
