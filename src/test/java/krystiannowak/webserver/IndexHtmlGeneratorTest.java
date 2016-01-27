package krystiannowak.webserver;

import static krystiannowak.webserver.SimpleStringSerialization.deserialize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import com.google.common.io.Resources;

public class IndexHtmlGeneratorTest {

    private IndexHtmlGenerator sut = new IndexHtmlGenerator(
            new File(Server.DEFAULT_DOCUMENT_ROOT), "/a/aa",
            Optional.of("127.0.0.1:31337"));

    @Test
    public void htmlReflectsDirectoryContent() throws IOException {
        String html = sut.getHtml();

        assertTrue("written message should not be empty", html.length() > 0);

        String example = deserialize(readExample());

        assertEquals("generated HTML should match the example", example, html);
    }

    private byte[] readExample() throws IOException {
        return Resources
                .toByteArray(getClass().getResource("/directoryListing.html"));
    }

}
