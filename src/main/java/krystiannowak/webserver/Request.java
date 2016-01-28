package krystiannowak.webserver;

/**
 * A (simplified) representation of an HTTP request roughly based on
 * <a href="https://tools.ietf.org/html/rfc2616">https://tools.ietf.org/html/
 * rfc2616</a>.
 *
 * @author krystiannowak
 *
 */
public class Request implements Message {

    /**
     * Method as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-5.1.1">https://tools
     * .ietf.org/html/rfc2616#section-5.1.1</a>.
     */
    private String method;

    /**
     * Request-URI as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-5.1.2">https://tools
     * .ietf.org/html/rfc2616#section-5.1.2</a>.
     */
    private String requestUri;

    /**
     * HTTP Version as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-3.1">https://tools.
     * ietf.org/html/rfc2616#section-3.1</a>.
     */
    private String httpVersion;

    /**
     * Host header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.23">https://tools
     * .ietf.org/html/rfc2616#section-14.23</a>.
     */
    private String host;

    /**
     * Referer header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.36">https://tools
     * .ietf.org/html/rfc2616#section-14.36</a>.
     */
    private String referer;

    /**
     * User-Agent header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.43">https://tools
     * .ietf.org/html/rfc2616#section-14.43</a>.
     */
    private String userAgent;

    /**
     * Accept header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.1">https://tools.
     * ietf.org/html/rfc2616#section-14.1</a>.
     */
    private String accept;

    /**
     * Accept-Language header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.4">https://tools.
     * ietf.org/html/rfc2616#section-14.4</a>.
     */
    private String acceptLanguage;

    /**
     * Accept-Encoding header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.3">https://tools.
     * ietf.org/html/rfc2616#section-14.3</a>.
     */
    private String acceptEncoding;

    /**
     * Connection header as per
     * <a href="https://tools.ietf.org/html/rfc2616#section-14.10">https://tools
     * .ietf.org/html/rfc2616#section-14.10</a>.
     */
    private String connection;

    /**
     * Method getter.
     *
     * @return Method value
     */
    public final String getMethod() {
        return method;
    }

    /**
     * Method setter.
     *
     * @param method
     *            Method value
     */
    public final void setMethod(final String method) {
        this.method = method;
    }

    /**
     * Request URI getter.
     *
     * @return Request URI value
     */
    public final String getRequestUri() {
        return requestUri;
    }

    /**
     * Request URI setter.
     *
     * @param requestUri
     *            Request URI value
     */
    public final void setRequestUri(final String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * HTTP Version getter.
     *
     * @return HTTP Version value
     */
    public final String getHttpVersion() {
        return httpVersion;
    }

    /**
     * HTTP Version setter.
     *
     * @param httpVersion
     *            HTTP Version value
     */
    public final void setHttpVersion(final String httpVersion) {
        this.httpVersion = httpVersion;
    }

    /**
     * Host header getter.
     *
     * @return Host header value
     */
    public final String getHost() {
        return host;
    }

    /**
     * Host header setter.
     *
     * @param host
     *            Host header value
     */
    public final void setHost(final String host) {
        this.host = host;
    }

    /**
     * Referer header getter.
     *
     * @return Referer header value
     */
    public final String getReferer() {
        return referer;
    }

    /**
     * Referer header setter.
     *
     * @param referer
     *            Referer header value
     */
    public final void setReferer(final String referer) {
        this.referer = referer;
    }

    /**
     * User-Agent header getter.
     *
     * @return User-Agent header value
     */
    public final String getUserAgent() {
        return userAgent;
    }

    /**
     * User-Agent header setter.
     *
     * @param userAgent
     *            User-Agent header value
     */
    public final void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * Accept header getter.
     *
     * @return Accept header value
     */
    public final String getAccept() {
        return accept;
    }

    /**
     * Accept header setter.
     *
     * @param accept
     *            Accept header value
     */
    public final void setAccept(final String accept) {
        this.accept = accept;
    }

    /**
     * Accept-Language header getter.
     *
     * @return Accept-Language header value
     */
    public final String getAcceptLanguage() {
        return acceptLanguage;
    }

    /**
     * Accept-Language header setter.
     *
     * @param acceptLanguage
     *            Accept-Language header value
     */
    public final void setAcceptLanguage(final String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * Accept-Encoding header getter.
     *
     * @return Accept-Encoding header value
     */
    public final String getAcceptEncoding() {
        return acceptEncoding;
    }

    /**
     * Accept-Encoding header setter.
     *
     * @param acceptEncoding
     *            Accept-Encoding header value
     */
    public final void setAcceptEncoding(final String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    /**
     * Connection header getter.
     *
     * @return Connection header value
     */
    public final String getConnection() {
        return connection;
    }

    /**
     * Connection header setter.
     *
     * @param connection
     *            Connection header value
     */
    public final void setConnection(final String connection) {
        this.connection = connection;
    }

    /**
     * Tests if Keep-Alive behaviour is requested.
     *
     * @return is Keep-Alive requested
     */
    public final boolean isKeepAlive() {
        return "Keep-Alive".equalsIgnoreCase(getConnection());
    }

    @Override
    public final String toString() {
        return "Request [method=" + method + ", requestUri=" + requestUri
                + ", httpVersion=" + httpVersion + ", host=" + host
                + ", userAgent=" + userAgent + ", accept=" + accept
                + ", acceptLanguage=" + acceptLanguage + ", acceptEncoding="
                + acceptEncoding + ", connection=" + connection + "]";
    }

}
