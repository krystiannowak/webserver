package krystiannowak.webserver;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A tool for simple {@link String} <-> byte array serialization/deserialization
 * using default (simplified) {@link Charset}.
 *
 * @author krystiannowak
 *
 */
public final class SimpleStringSerialization {

    /**
     * Default charset chosen for simplicity.
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;

    /**
     * No instantiation possible.
     */
    private SimpleStringSerialization() {
    }

    /**
     * Deserializes a {@link String} from a byte array given using default
     * (simplified) {@link Charset}.
     *
     * @param data
     *            a byte array to deserialize a {@link String} from
     * @return deserialized {@link String}
     */
    public static String deserialize(final byte[] data) {
        return new String(data, DEFAULT_CHARSET);
    }

    /**
     * Serializes a given {@link String} into a byte array using default
     * (simplified) {@link Charset}.
     *
     * @param string
     *            a {@link String} to serialize into a byte array
     * @return serialized byte array
     */
    public static byte[] serialize(final String string) {
        return string.getBytes(DEFAULT_CHARSET);
    }

}
