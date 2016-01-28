package krystiannowak.webserver;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.google.common.net.MediaType;

public class MimeTypeUtilsTest {

    @Test
    public void mediaTypesAreProperlyRecognised() {
        checkMediaType(MediaType.PLAIN_TEXT_UTF_8.withoutParameters(),
                "/a/aa/aafile1");
        checkMediaType(MediaType.JPEG, "/a/aa/aafile2.jpg");
        checkMediaType(MediaType.GIF, "/a/aa/aafile3.gif");
        checkMediaType(MediaType.OCTET_STREAM, "/a/aa/aafile4");
    }

    private void checkMediaType(MediaType expectedType, String filePath) {
        File file = new File(Server.DEFAULT_DOCUMENT_ROOT, filePath);

        assertEquals("mime type should match", expectedType.toString(),
                MimeTypeUtils.guessContentType(file));
    }

}
