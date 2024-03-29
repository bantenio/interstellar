package org.tenio.interstellar.service.http;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: HttpHeader
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 11:50
 * &#064;version: 1.0
 */
public class HttpHeader implements MultiValuedMap<String, String>, Serializable {

    private static final long serialVersionUID = -5154000334826494334L;

    // region HTTP header names
    /**
     * The HTTP {@code Accept} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">Section 5.3.2 of RFC 7231</a>
     */
    public static final String ACCEPT = "Accept";
    /**
     * The HTTP {@code Accept-Charset} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.3">Section 5.3.3 of RFC 7231</a>
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    /**
     * The HTTP {@code Accept-Encoding} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.4">Section 5.3.4 of RFC 7231</a>
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    /**
     * The HTTP {@code Accept-Language} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.5">Section 5.3.5 of RFC 7231</a>
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    /**
     * The HTTP {@code Accept-Patch} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5789#section-3.1">Section 3.1 of RFC 5789</a>
     * @since 5.3.6
     */
    public static final String ACCEPT_PATCH = "Accept-Patch";
    /**
     * The HTTP {@code Accept-Ranges} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-2.3">Section 5.3.5 of RFC 7233</a>
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    /**
     * The CORS {@code Access-Control-Allow-Credentials} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    /**
     * The CORS {@code Access-Control-Allow-Headers} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    /**
     * The CORS {@code Access-Control-Allow-Methods} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    /**
     * The CORS {@code Access-Control-Allow-Origin} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    /**
     * The CORS {@code Access-Control-Expose-Headers} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    /**
     * The CORS {@code Access-Control-Max-Age} response header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    /**
     * The CORS {@code Access-Control-Request-Headers} request header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    /**
     * The CORS {@code Access-Control-Request-Method} request header field name.
     *
     * @see <a href="https://www.w3.org/TR/cors/">CORS W3C recommendation</a>
     */
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    /**
     * The HTTP {@code Age} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.1">Section 5.1 of RFC 7234</a>
     */
    public static final String AGE = "Age";
    /**
     * The HTTP {@code Allow} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.4.1">Section 7.4.1 of RFC 7231</a>
     */
    public static final String ALLOW = "Allow";
    /**
     * The HTTP {@code Authorization} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.2">Section 4.2 of RFC 7235</a>
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * The HTTP {@code Cache-Control} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.2">Section 5.2 of RFC 7234</a>
     */
    public static final String CACHE_CONTROL = "Cache-Control";
    /**
     * The HTTP {@code Connection} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-6.1">Section 6.1 of RFC 7230</a>
     */
    public static final String CONNECTION = "Connection";
    /**
     * The HTTP {@code Content-Encoding} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.2.2">Section 3.1.2.2 of RFC 7231</a>
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";
    /**
     * The HTTP {@code Content-Disposition} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6266">RFC 6266</a>
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    /**
     * The HTTP {@code Content-Language} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.3.2">Section 3.1.3.2 of RFC 7231</a>
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";
    /**
     * The HTTP {@code Content-Length} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-3.3.2">Section 3.3.2 of RFC 7230</a>
     */
    public static final String CONTENT_LENGTH = "Content-Length";
    /**
     * The HTTP {@code Content-Location} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.4.2">Section 3.1.4.2 of RFC 7231</a>
     */
    public static final String CONTENT_LOCATION = "Content-Location";
    /**
     * The HTTP {@code Content-Range} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-4.2">Section 4.2 of RFC 7233</a>
     */
    public static final String CONTENT_RANGE = "Content-Range";
    /**
     * The HTTP {@code Content-Type} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-3.1.1.5">Section 3.1.1.5 of RFC 7231</a>
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * The HTTP {@code Cookie} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2109#section-4.3.4">Section 4.3.4 of RFC 2109</a>
     */
    public static final String COOKIE = "Cookie";
    /**
     * The HTTP {@code Date} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.2">Section 7.1.1.2 of RFC 7231</a>
     */
    public static final String DATE = "Date";
    /**
     * The HTTP {@code ETag} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.3">Section 2.3 of RFC 7232</a>
     */
    public static final String ETAG = "ETag";
    /**
     * The HTTP {@code Expect} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.1.1">Section 5.1.1 of RFC 7231</a>
     */
    public static final String EXPECT = "Expect";
    /**
     * The HTTP {@code Expires} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.3">Section 5.3 of RFC 7234</a>
     */
    public static final String EXPIRES = "Expires";
    /**
     * The HTTP {@code From} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.1">Section 5.5.1 of RFC 7231</a>
     */
    public static final String FROM = "From";
    /**
     * The HTTP {@code Host} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-5.4">Section 5.4 of RFC 7230</a>
     */
    public static final String HOST = "Host";
    /**
     * The HTTP {@code If-Match} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.1">Section 3.1 of RFC 7232</a>
     */
    public static final String IF_MATCH = "If-Match";
    /**
     * The HTTP {@code If-Modified-Since} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.3">Section 3.3 of RFC 7232</a>
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
    /**
     * The HTTP {@code If-None-Match} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.2">Section 3.2 of RFC 7232</a>
     */
    public static final String IF_NONE_MATCH = "If-None-Match";
    /**
     * The HTTP {@code If-Range} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-3.2">Section 3.2 of RFC 7233</a>
     */
    public static final String IF_RANGE = "If-Range";
    /**
     * The HTTP {@code If-Unmodified-Since} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-3.4">Section 3.4 of RFC 7232</a>
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    /**
     * The HTTP {@code Last-Modified} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.2">Section 2.2 of RFC 7232</a>
     */
    public static final String LAST_MODIFIED = "Last-Modified";
    /**
     * The HTTP {@code Link} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5988">RFC 5988</a>
     */
    public static final String LINK = "Link";
    /**
     * The HTTP {@code Location} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.2">Section 7.1.2 of RFC 7231</a>
     */
    public static final String LOCATION = "Location";
    /**
     * The HTTP {@code Max-Forwards} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.1.2">Section 5.1.2 of RFC 7231</a>
     */
    public static final String MAX_FORWARDS = "Max-Forwards";
    /**
     * The HTTP {@code Origin} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc6454">RFC 6454</a>
     */
    public static final String ORIGIN = "Origin";
    /**
     * The HTTP {@code Pragma} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.4">Section 5.4 of RFC 7234</a>
     */
    public static final String PRAGMA = "Pragma";
    /**
     * The HTTP {@code Proxy-Authenticate} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.3">Section 4.3 of RFC 7235</a>
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";
    /**
     * The HTTP {@code Proxy-Authorization} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.4">Section 4.4 of RFC 7235</a>
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";
    /**
     * The HTTP {@code Range} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7233#section-3.1">Section 3.1 of RFC 7233</a>
     */
    public static final String RANGE = "Range";
    /**
     * The HTTP {@code Referer} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.2">Section 5.5.2 of RFC 7231</a>
     */
    public static final String REFERER = "Referer";
    /**
     * The HTTP {@code Retry-After} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.3">Section 7.1.3 of RFC 7231</a>
     */
    public static final String RETRY_AFTER = "Retry-After";
    /**
     * The HTTP {@code Server} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.4.2">Section 7.4.2 of RFC 7231</a>
     */
    public static final String SERVER = "Server";
    /**
     * The HTTP {@code Set-Cookie} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2109#section-4.2.2">Section 4.2.2 of RFC 2109</a>
     */
    public static final String SET_COOKIE = "Set-Cookie";
    /**
     * The HTTP {@code Set-Cookie2} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc2965">RFC 2965</a>
     */
    public static final String SET_COOKIE2 = "Set-Cookie2";
    /**
     * The HTTP {@code TE} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-4.3">Section 4.3 of RFC 7230</a>
     */
    public static final String TE = "TE";
    /**
     * The HTTP {@code Trailer} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-4.4">Section 4.4 of RFC 7230</a>
     */
    public static final String TRAILER = "Trailer";
    /**
     * The HTTP {@code Transfer-Encoding} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-3.3.1">Section 3.3.1 of RFC 7230</a>
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    /**
     * The HTTP {@code Upgrade} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-6.7">Section 6.7 of RFC 7230</a>
     */
    public static final String UPGRADE = "Upgrade";
    /**
     * The HTTP {@code User-Agent} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.5.3">Section 5.5.3 of RFC 7231</a>
     */
    public static final String USER_AGENT = "User-Agent";
    /**
     * The HTTP {@code Vary} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.4">Section 7.1.4 of RFC 7231</a>
     */
    public static final String VARY = "Vary";
    /**
     * The HTTP {@code Via} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7230#section-5.7.1">Section 5.7.1 of RFC 7230</a>
     */
    public static final String VIA = "Via";
    /**
     * The HTTP {@code Warning} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7234#section-5.5">Section 5.5 of RFC 7234</a>
     */
    public static final String WARNING = "Warning";
    /**
     * The HTTP {@code WWW-Authenticate} header field name.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7235#section-4.1">Section 4.1 of RFC 7235</a>
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    // endregion

    // region static components
    /**
     * Pattern matching ETag multiple field values in headers such as "If-Match", "If-None-Match".
     *
     * @see <a href="https://tools.ietf.org/html/rfc7232#section-2.3">Section 2.3 of RFC 7232</a>
     */
    private static final Pattern ETAG_HEADER_VALUE_PATTERN = Pattern.compile("\\*|\\s*((W\\/)?(\"[^\"]*\"))\\s*,?");

    private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols(Locale.ENGLISH);

    private static final ZoneId GMT = ZoneId.of("GMT");

    /**
     * Date formats with time zone as specified in the HTTP RFC to use for formatting.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).withZone(GMT);

    /**
     * Date formats with time zone as specified in the HTTP RFC to use for parsing.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    private static final DateTimeFormatter[] DATE_PARSERS = new DateTimeFormatter[]{
            DateTimeFormatter.RFC_1123_DATE_TIME,
            DateTimeFormatter.ofPattern("EEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
            DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy", Locale.US).withZone(GMT)
    };
    // endregion
    /**
     * Header values container
     */
    private final MultiValuedMap<String, String> headers;

    /**
     * Create an empty HttpHeader
     */
    public HttpHeader() {
        headers = new HashSetValuedHashMap<>();
    }

    /**
     * TODO
     *
     * @param headers TODO
     */
    public HttpHeader(MultiValuedMap<String, String> headers) {
        this.headers = headers;
    }

    //

    /**
     * Get the list of header values for the given header name, if any.
     *
     * @param headerName the header name
     * @return the list of header values, or an empty list
     * @since 5.2
     */
    public Collection<String> getOrEmpty(String headerName) {
        Collection<String> values = get(headerName);
        return (values != null ? values : Collections.emptyList());
    }

    /**
     * Set the list of acceptable {@linkplain MediaType media types},
     * as specified by the {@code Accept} header.
     *
     * @param acceptableMediaTypes TODO
     */
    public void setAccept(List<MediaType> acceptableMediaTypes) {
        set(ACCEPT, MediaType.toString(acceptableMediaTypes));
    }

    /**
     * Return the list of acceptable {@linkplain MediaType media types},
     * as specified by the {@code Accept} header.
     * <p>Returns an empty list when the acceptable media types are unspecified.
     *
     * @return accept media type collection
     */
    public List<MediaType> getAccept() {
        return MediaType.parseMediaTypes(get(ACCEPT));
    }

    /**
     * Set the acceptable language ranges, as specified by the
     * {@literal Accept-Language} header.
     *
     * @param languages TODO
     * @since 5.0
     */
    public void setAcceptLanguage(List<Locale.LanguageRange> languages) {
        Assert.notNull(languages, "LanguageRange List must not be null");
        DecimalFormat decimal = new DecimalFormat("0.0", DECIMAL_FORMAT_SYMBOLS);
        List<String> values = languages.stream()
                .map(range ->
                        range.getWeight() == Locale.LanguageRange.MAX_WEIGHT ?
                                range.getRange() :
                                range.getRange() + ";q=" + decimal.format(range.getWeight()))
                .collect(Collectors.toList());
        set(ACCEPT_LANGUAGE, toCommaDelimitedString(values));
    }

    /**
     * Return the language ranges from the {@literal "Accept-Language"} header.
     * <p>If you only need sorted, preferred locales only use
     * {@link #getAcceptLanguageAsLocales()} or if you need to filter based on
     * a list of supported locales you can pass the returned list to
     * {@link Locale#filter(List, Collection)}.
     *
     * @return TODO
     * @throws IllegalArgumentException if the value cannot be converted to a language range
     * @since 5.0
     */
    public List<Locale.LanguageRange> getAcceptLanguage() {
        String value = getFirst(ACCEPT_LANGUAGE);
        return (CharSequenceUtil.isNotEmpty(value) ? Locale.LanguageRange.parse(value) : Collections.emptyList());
    }

    /**
     * Variant of {@link #setAcceptLanguage(List)} using {@link Locale}'s.
     *
     * @param locales TODO
     * @since 5.0
     */
    public void setAcceptLanguageAsLocales(List<Locale> locales) {
        setAcceptLanguage(locales.stream()
                .map(locale -> new Locale.LanguageRange(locale.toLanguageTag()))
                .collect(Collectors.toList()));
    }

    /**
     * A variant of {@link #getAcceptLanguage()} that converts each
     * {@link java.util.Locale.LanguageRange} to a {@link Locale}.
     *
     * @return the locales or an empty list
     * @throws IllegalArgumentException if the value cannot be converted to a locale
     * @since 5.0
     */
    public List<Locale> getAcceptLanguageAsLocales() {
        List<Locale.LanguageRange> ranges = getAcceptLanguage();
        if (ranges.isEmpty()) {
            return Collections.emptyList();
        }
        return ranges.stream()
                .map(range -> Locale.forLanguageTag(range.getRange()))
                .filter(locale -> CharSequenceUtil.isNotEmpty(locale.getDisplayName()))
                .collect(Collectors.toList());
    }

    /**
     * Set the list of acceptable {@linkplain MediaType media types} for
     * {@code PATCH} methods, as specified by the {@code Accept-Patch} header.
     *
     * @param mediaTypes TODO
     * @since 5.3.6
     */
    public void setAcceptPatch(List<MediaType> mediaTypes) {
        set(ACCEPT_PATCH, MediaType.toString(mediaTypes));
    }

    /**
     * Return the list of acceptable {@linkplain MediaType media types} for
     * {@code PATCH} methods, as specified by the {@code Accept-Patch} header.
     * <p>Returns an empty list when the acceptable media types are unspecified.
     *
     * @return TODO
     * @since 5.3.6
     */
    public List<MediaType> getAcceptPatch() {
        return MediaType.parseMediaTypes(get(ACCEPT_PATCH));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Credentials} response header.
     *
     * @param allowCredentials TODO
     */
    public void setAccessControlAllowCredentials(boolean allowCredentials) {
        set(ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.toString(allowCredentials));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Credentials} response header.
     *
     * @return TODO
     */
    public boolean getAccessControlAllowCredentials() {
        return Boolean.parseBoolean(getFirst(ACCESS_CONTROL_ALLOW_CREDENTIALS));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Headers} response header.
     *
     * @param allowedHeaders TODO
     */
    public void setAccessControlAllowHeaders(List<String> allowedHeaders) {
        set(ACCESS_CONTROL_ALLOW_HEADERS, toCommaDelimitedString(allowedHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Headers} response header.
     *
     * @return TODO
     */
    public List<String> getAccessControlAllowHeaders() {
        return getValuesAsList(ACCESS_CONTROL_ALLOW_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Methods} response header.
     *
     * @param allowedMethods TODO
     */
    public void setAccessControlAllowMethods(List<HttpMethod> allowedMethods) {
        set(ACCESS_CONTROL_ALLOW_METHODS, CollUtil.join(allowedMethods, ","));
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Methods} response header.
     *
     * @return TODO
     */
    public List<HttpMethod> getAccessControlAllowMethods() {
        String value = getFirst(ACCESS_CONTROL_ALLOW_METHODS);
        if (value != null) {
            String[] tokens = CharSequenceUtil.split(value, ',', true, true).toArray(new String[0]);
            List<HttpMethod> result = new ArrayList<>(tokens.length);
            for (String token : tokens) {
                HttpMethod method = HttpMethod.valueOf(token);
                result.add(method);
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Set the (new) value of the {@code Access-Control-Allow-Origin} response header.
     *
     * @param allowedOrigin TODO
     */
    public void setAccessControlAllowOrigin(String allowedOrigin) {
        setOrRemove(ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigin);
    }

    /**
     * Return the value of the {@code Access-Control-Allow-Origin} response header.
     *
     * @return TODO
     */
    public String getAccessControlAllowOrigin() {
        return getFieldValues(ACCESS_CONTROL_ALLOW_ORIGIN);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Expose-Headers} response header.
     *
     * @param exposedHeaders TODO
     */
    public void setAccessControlExposeHeaders(List<String> exposedHeaders) {
        set(ACCESS_CONTROL_EXPOSE_HEADERS, toCommaDelimitedString(exposedHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Expose-Headers} response header.
     *
     * @return TODO
     */
    public List<String> getAccessControlExposeHeaders() {
        return getValuesAsList(ACCESS_CONTROL_EXPOSE_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Max-Age} response header.
     *
     * @param maxAge TODO
     * @since 5.2
     */
    public void setAccessControlMaxAge(Duration maxAge) {
        set(ACCESS_CONTROL_MAX_AGE, Long.toString(maxAge.getSeconds()));
    }

    /**
     * Set the (new) value of the {@code Access-Control-Max-Age} response header.
     *
     * @param maxAge TODO
     */
    public void setAccessControlMaxAge(long maxAge) {
        set(ACCESS_CONTROL_MAX_AGE, Long.toString(maxAge));
    }

    /**
     * Return the value of the {@code Access-Control-Max-Age} response header.
     * <p>Returns -1 when the max age is unknown.
     *
     * @return TODO
     */
    public long getAccessControlMaxAge() {
        String value = getFirst(ACCESS_CONTROL_MAX_AGE);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Request-Headers} request header.
     *
     * @param requestHeaders TODO
     */
    public void setAccessControlRequestHeaders(List<String> requestHeaders) {
        set(ACCESS_CONTROL_REQUEST_HEADERS, toCommaDelimitedString(requestHeaders));
    }

    /**
     * Return the value of the {@code Access-Control-Request-Headers} request header.
     *
     * @return TODO
     */
    public List<String> getAccessControlRequestHeaders() {
        return getValuesAsList(ACCESS_CONTROL_REQUEST_HEADERS);
    }

    /**
     * Set the (new) value of the {@code Access-Control-Request-Method} request header.
     *
     * @param requestMethod TODO
     */
    public void setAccessControlRequestMethod(HttpMethod requestMethod) {
        setOrRemove(ACCESS_CONTROL_REQUEST_METHOD, (requestMethod != null ? requestMethod.name() : null));
    }

    /**
     * Return the value of the {@code Access-Control-Request-Method} request header.
     *
     * @return TODO
     */
    public HttpMethod getAccessControlRequestMethod() {
        String requestMethod = getFirst(ACCESS_CONTROL_REQUEST_METHOD);
        if (requestMethod != null) {
            return HttpMethod.valueOf(requestMethod);
        } else {
            return null;
        }
    }

    /**
     * Set the list of acceptable {@linkplain Charset charsets},
     * as specified by the {@code Accept-Charset} header.
     *
     * @param acceptableCharsets TODO
     */
    public void setAcceptCharset(List<Charset> acceptableCharsets) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Charset charset : acceptableCharsets) {
            joiner.add(charset.name().toLowerCase(Locale.ENGLISH));
        }
        set(ACCEPT_CHARSET, joiner.toString());
    }

    /**
     * Return the list of acceptable {@linkplain Charset charsets},
     * as specified by the {@code Accept-Charset} header.
     *
     * @return TODO
     */
    public List<Charset> getAcceptCharset() {
        String value = getFirst(ACCEPT_CHARSET);
        if (value != null) {
            String[] tokens = CharSequenceUtil.split(value, ',', true, true).toArray(new String[0]);
            List<Charset> result = new ArrayList<>(tokens.length);
            for (String token : tokens) {
                int paramIdx = token.indexOf(';');
                String charsetName;
                if (paramIdx == -1) {
                    charsetName = token;
                } else {
                    charsetName = token.substring(0, paramIdx);
                }
                if (!charsetName.equals("*")) {
                    result.add(Charset.forName(charsetName));
                }
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Set the set of allowed {@link HttpMethod HTTP methods},
     * as specified by the {@code Allow} header.
     *
     * @param allowedMethods TODO
     */
    public void setAllow(Set<HttpMethod> allowedMethods) {
        set(ALLOW, CollUtil.join(allowedMethods, ","));
    }

    /**
     * Return the set of allowed {@link HttpMethod HTTP methods},
     * as specified by the {@code Allow} header.
     * <p>Returns an empty set when the allowed methods are unspecified.
     *
     * @return TODO
     */
    public Set<HttpMethod> getAllow() {
        String value = getFirst(ALLOW);
        if (CharSequenceUtil.isNotEmpty(value)) {
            String[] tokens = CharSequenceUtil.split(value, ',', true, true).toArray(new String[0]);
            Set<HttpMethod> result = new LinkedHashSet<>(tokens.length);
            for (String token : tokens) {
                HttpMethod method = HttpMethod.valueOf(token);
                result.add(method);
            }
            return result;
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given username and password.
     * <p>Note that this method only supports characters in the
     * {@link StandardCharsets#ISO_8859_1 ISO-8859-1} character set.
     *
     * @param username the username
     * @param password the password
     * @throws IllegalArgumentException if either {@code user} or
     *                                  {@code password} contain characters that cannot be encoded to ISO-8859-1
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     * @since 5.1
     */
    public void setBasicAuth(String username, String password) {
        setBasicAuth(username, password, null);
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given username and password.
     *
     * @param username the username
     * @param password the password
     * @param charset  the charset to use to convert the credentials into an octet
     *                 sequence. Defaults to {@linkplain StandardCharsets#ISO_8859_1 ISO-8859-1}.
     * @throws IllegalArgumentException if {@code username} or {@code password}
     *                                  contains characters that cannot be encoded to the given charset
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     * @since 5.1
     */
    public void setBasicAuth(String username, String password, Charset charset) {
        setBasicAuth(encodeBasicAuth(username, password, charset));
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * Basic Authentication based on the given {@linkplain #encodeBasicAuth
     * encoded credentials}.
     * <p>Favor this method over {@link #setBasicAuth(String, String)} and
     * {@link #setBasicAuth(String, String, Charset)} if you wish to cache the
     * encoded credentials.
     *
     * @param encodedCredentials the encoded credentials
     * @throws IllegalArgumentException if supplied credentials string is
     *                                  {@code null} or blank
     * @see #setBasicAuth(String, String)
     * @see #setBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     * @since 5.2
     */
    public void setBasicAuth(String encodedCredentials) {
        Assert.notEmpty(encodedCredentials, "'encodedCredentials' must not be null or blank");
        set(AUTHORIZATION, "Basic " + encodedCredentials);
    }

    /**
     * Set the value of the {@linkplain #AUTHORIZATION Authorization} header to
     * the given Bearer token.
     *
     * @param token the Base64 encoded token
     * @see <a href="https://tools.ietf.org/html/rfc6750">RFC 6750</a>
     * @since 5.1
     */
    public void setBearerAuth(String token) {
        set(AUTHORIZATION, "Bearer " + token);
    }

    /**
     * Set a configured {@link CacheControl} instance as the
     * new value of the {@code Cache-Control} header.
     *
     * @param cacheControl TODO
     * @since 5.0.5
     */
    public void setCacheControl(CacheControl cacheControl) {
        setOrRemove(CACHE_CONTROL, cacheControl.getHeaderValue());
    }

    /**
     * Set the (new) value of the {@code Cache-Control} header.
     *
     * @param cacheControl TODO
     */
    public void setCacheControl(String cacheControl) {
        setOrRemove(CACHE_CONTROL, cacheControl);
    }

    /**
     * Return the value of the {@code Cache-Control} header.
     *
     * @return TODO
     */
    public String getCacheControl() {
        return getFieldValues(CACHE_CONTROL);
    }

    /**
     * Set the (new) value of the {@code Connection} header.
     *
     * @param connection TODO
     */
    public void setConnection(String connection) {
        set(CONNECTION, connection);
    }

    /**
     * Set the (new) value of the {@code Connection} header.
     *
     * @param connection TODO
     */
    public void setConnection(List<String> connection) {
        set(CONNECTION, toCommaDelimitedString(connection));
    }

    /**
     * Return the value of the {@code Connection} header.
     *
     * @return TODO
     */
    public List<String> getConnection() {
        return getValuesAsList(CONNECTION);
    }

    /**
     * Set the {@link Locale} of the content language,
     * as specified by the {@literal Content-Language} header.
     * <p>Use {@code put(CONTENT_LANGUAGE, list)} if you need
     * to set multiple content languages.</p>
     *
     * @param locale TODO
     * @since 5.0
     */
    public void setContentLanguage(Locale locale) {
        setOrRemove(CONTENT_LANGUAGE, (locale != null ? locale.toLanguageTag() : null));
    }

    /**
     * Get the first {@link Locale} of the content languages, as specified by the
     * {@code Content-Language} header.
     * <p>Use {@link #getValuesAsList(String)} if you need to get multiple content
     * languages.
     *
     * @return the first {@code Locale} of the content languages, or {@code null}
     * if unknown
     * @since 5.0
     */
    public Locale getContentLanguage() {
        return getValuesAsList(CONTENT_LANGUAGE)
                .stream()
                .findFirst()
                .map(Locale::forLanguageTag)
                .orElse(null);
    }

    /**
     * Set the length of the body in bytes, as specified by the
     * {@code Content-Length} header.
     *
     * @param contentLength TODO
     */
    public void setContentLength(long contentLength) {
        set(CONTENT_LENGTH, Long.toString(contentLength));
    }

    /**
     * Return the length of the body in bytes, as specified by the
     * {@code Content-Length} header.
     * <p>Returns -1 when the content-length is unknown.
     *
     * @return TODO
     */
    public long getContentLength() {
        String value = getFirst(CONTENT_LENGTH);
        return (value != null ? Long.parseLong(value) : -1);
    }

    /**
     * Set the {@linkplain MediaType media type} of the body,
     * as specified by the {@code Content-Type} header.
     *
     * @param mediaType TODO
     */
    public void setContentType(MediaType mediaType) {
        if (mediaType != null) {
            Assert.isTrue(!mediaType.isWildcardType(), "Content-Type cannot contain wildcard type '*'");
            Assert.isTrue(!mediaType.isWildcardSubtype(), "Content-Type cannot contain wildcard subtype '*'");
            set(CONTENT_TYPE, mediaType.toString());
        } else {
            remove(CONTENT_TYPE);
        }
    }

    /**
     * Return the {@linkplain MediaType media type} of the body, as specified
     * by the {@code Content-Type} header.
     * <p>Returns {@code null} when the {@code Content-Type} header is not set.
     *
     * @return TODO
     * @throws InvalidMediaTypeException if the media type value cannot be parsed
     */
    public MediaType getContentType() {
        String value = getFirst(CONTENT_TYPE);
        return (CharSequenceUtil.isNotEmpty(value) ? MediaType.parseMediaType(value) : null);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     *
     * @param date TODO
     * @since 5.2
     */
    public void setDate(ZonedDateTime date) {
        setZonedDateTime(DATE, date);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     *
     * @param date TODO
     * @since 5.2
     */
    public void setDate(Instant date) {
        setInstant(DATE, date);
    }

    /**
     * Set the date and time at which the message was created, as specified
     * by the {@code Date} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param date TODO
     */
    public void setDate(long date) {
        setDate(DATE, date);
    }

    /**
     * Return the date and time at which the message was created, as specified
     * by the {@code Date} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @throws IllegalArgumentException if the value cannot be converted to a date
     */
    public long getDate() {
        return getFirstDate(DATE);
    }

    /**
     * Set the (new) entity tag of the body, as specified by the {@code ETag} header.
     *
     * @param etag TODO
     */
    public void setETag(String etag) {
        if (etag != null) {
            Assert.isTrue(etag.startsWith("\"") || etag.startsWith("W/"),
                    "Invalid ETag: does not start with W/ or \"");
            Assert.isTrue(etag.endsWith("\""), "Invalid ETag: does not end with \"");
            set(ETAG, etag);
        } else {
            remove(ETAG);
        }
    }

    /**
     * Return the entity tag of the body, as specified by the {@code ETag} header.
     *
     * @return TODO
     */
    public String getETag() {
        return getFirst(ETAG);
    }

    /**
     * Set the duration after which the message is no longer valid,
     * as specified by the {@code Expires} header.
     *
     * @param expires TODO
     * @since 5.0.5
     */
    public void setExpires(ZonedDateTime expires) {
        setZonedDateTime(EXPIRES, expires);
    }

    /**
     * Set the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     *
     * @param expires TODO
     * @since 5.2
     */
    public void setExpires(Instant expires) {
        setInstant(EXPIRES, expires);
    }

    /**
     * Set the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param expires TODO
     */
    public void setExpires(long expires) {
        setDate(EXPIRES, expires);
    }

    /**
     * Return the date and time at which the message is no longer valid,
     * as specified by the {@code Expires} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getExpires() {
        return getFirstDate(EXPIRES, false);
    }

    /**
     * Set the (new) value of the {@code Host} header.
     * <p>If the given {@linkplain InetSocketAddress#getPort() port} is {@code 0},
     * the host header will only contain the
     * {@linkplain InetSocketAddress#getHostString() host name}.
     *
     * @param host TODO
     * @since 5.0
     */
    public void setHost(InetSocketAddress host) {
        if (host != null) {
            String value = host.getHostString();
            int port = host.getPort();
            if (port != 0) {
                value = value + ":" + port;
            }
            set(HOST, value);
        } else {
            remove(HOST);
        }
    }

    /**
     * Return the value of the {@code Host} header, if available.
     * <p>If the header value does not contain a port, the
     * {@linkplain InetSocketAddress#getPort() port} in the returned address will
     * be {@code 0}.
     *
     * @return TODO
     * @since 5.0
     */
    public InetSocketAddress getHost() {
        String value = getFirst(HOST);
        if (value == null) {
            return null;
        }

        String host = null;
        int port = 0;
        int separator = (value.startsWith("[") ? value.indexOf(':', value.indexOf(']')) : value.lastIndexOf(':'));
        if (separator != -1) {
            host = value.substring(0, separator);
            String portString = value.substring(separator + 1);
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException ex) {
                // ignore
            }
        }

        if (host == null) {
            host = value;
        }
        return InetSocketAddress.createUnresolved(host, port);
    }

    /**
     * Set the (new) value of the {@code If-Match} header.
     *
     * @param ifMatch TODO
     * @since 4.3
     */
    public void setIfMatch(String ifMatch) {
        set(IF_MATCH, ifMatch);
    }

    /**
     * Set the (new) value of the {@code If-Match} header.
     *
     * @param ifMatchList TODO
     * @since 4.3
     */
    public void setIfMatch(List<String> ifMatchList) {
        set(IF_MATCH, toCommaDelimitedString(ifMatchList));
    }

    /**
     * Return the value of the {@code If-Match} header.
     *
     * @return TODO
     * @throws IllegalArgumentException if parsing fails
     * @since 4.3
     */
    public List<String> getIfMatch() {
        return getETagValuesAsList(IF_MATCH);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifModifiedSince TODO
     * @since 5.1.4
     */
    public void setIfModifiedSince(ZonedDateTime ifModifiedSince) {
        setZonedDateTime(IF_MODIFIED_SINCE, ifModifiedSince.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifModifiedSince TODO
     * @since 5.1.4
     */
    public void setIfModifiedSince(Instant ifModifiedSince) {
        setInstant(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    /**
     * Set the (new) value of the {@code If-Modified-Since} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param ifModifiedSince TODO
     */
    public void setIfModifiedSince(long ifModifiedSince) {
        setDate(IF_MODIFIED_SINCE, ifModifiedSince);
    }

    /**
     * Return the value of the {@code If-Modified-Since} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getIfModifiedSince() {
        return getFirstDate(IF_MODIFIED_SINCE, false);
    }

    /**
     * Set the (new) value of the {@code If-None-Match} header.
     *
     * @param ifNoneMatch TODO
     */
    public void setIfNoneMatch(String ifNoneMatch) {
        set(IF_NONE_MATCH, ifNoneMatch);
    }

    /**
     * Set the (new) values of the {@code If-None-Match} header.
     *
     * @param ifNoneMatchList TODO
     */
    public void setIfNoneMatch(List<String> ifNoneMatchList) {
        set(IF_NONE_MATCH, toCommaDelimitedString(ifNoneMatchList));
    }

    /**
     * Return the value of the {@code If-None-Match} header.
     *
     * @return TODO
     * @throws IllegalArgumentException if parsing fails
     */
    public List<String> getIfNoneMatch() {
        return getETagValuesAsList(IF_NONE_MATCH);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifUnmodifiedSince TODO
     * @since 5.1.4
     */
    public void setIfUnmodifiedSince(ZonedDateTime ifUnmodifiedSince) {
        setZonedDateTime(IF_UNMODIFIED_SINCE, ifUnmodifiedSince.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param ifUnmodifiedSince TODO
     * @since 5.1.4
     */
    public void setIfUnmodifiedSince(Instant ifUnmodifiedSince) {
        setInstant(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    /**
     * Set the (new) value of the {@code If-Unmodified-Since} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param ifUnmodifiedSince TODO
     * @since 4.3
     */
    public void setIfUnmodifiedSince(long ifUnmodifiedSince) {
        setDate(IF_UNMODIFIED_SINCE, ifUnmodifiedSince);
    }

    /**
     * Return the value of the {@code If-Unmodified-Since} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     * @since 4.3
     */
    public long getIfUnmodifiedSince() {
        return getFirstDate(IF_UNMODIFIED_SINCE, false);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param lastModified TODO
     * @since 5.1.4
     */
    public void setLastModified(ZonedDateTime lastModified) {
        setZonedDateTime(LAST_MODIFIED, lastModified.withZoneSameInstant(GMT));
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     *
     * @param lastModified TODO
     * @since 5.1.4
     */
    public void setLastModified(Instant lastModified) {
        setInstant(LAST_MODIFIED, lastModified);
    }

    /**
     * Set the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     * <p>The date should be specified as the number of milliseconds since
     * January 1, 1970 GMT.
     *
     * @param lastModified TODO
     */
    public void setLastModified(long lastModified) {
        setDate(LAST_MODIFIED, lastModified);
    }

    /**
     * Return the time the resource was last changed, as specified by the
     * {@code Last-Modified} header.
     * <p>The date is returned as the number of milliseconds since
     * January 1, 1970 GMT. Returns -1 when the date is unknown.
     *
     * @return TODO
     * @see #getFirstZonedDateTime(String)
     */
    public long getLastModified() {
        return getFirstDate(LAST_MODIFIED, false);
    }

    /**
     * Set the (new) location of a resource,
     * as specified by the {@code Location} header.
     *
     * @param location TODO
     */
    public void setLocation(URI location) {
        setOrRemove(LOCATION, (location != null ? location.toASCIIString() : null));
    }

    /**
     * Return the (new) location of a resource
     * as specified by the {@code Location} header.
     * <p>Returns {@code null} when the location is unknown.
     *
     * @return TODO
     */
    public URI getLocation() {
        String value = getFirst(LOCATION);
        return (value != null ? URI.create(value) : null);
    }

    /**
     * Set the (new) value of the {@code Origin} header.
     *
     * @param origin TODO
     */
    public void setOrigin(String origin) {
        setOrRemove(ORIGIN, origin);
    }

    /**
     * Return the value of the {@code Origin} header.
     *
     * @return TODO
     */
    public String getOrigin() {
        return getFirst(ORIGIN);
    }

    /**
     * Set the (new) value of the {@code Pragma} header.
     *
     * @param pragma TODO
     */
    public void setPragma(String pragma) {
        setOrRemove(PRAGMA, pragma);
    }

    /**
     * Return the value of the {@code Pragma} header.
     *
     * @return TODO
     */
    public String getPragma() {
        return getFirst(PRAGMA);
    }

    /**
     * Set the (new) value of the {@code Upgrade} header.
     *
     * @param upgrade TODO
     */
    public void setUpgrade(String upgrade) {
        setOrRemove(UPGRADE, upgrade);
    }

    /**
     * Return the value of the {@code Upgrade} header.
     *
     * @return TODO
     */
    public String getUpgrade() {
        return getFirst(UPGRADE);
    }

    /**
     * Set the request header names (e.g. "Accept-Language") for which the
     * response is subject to content negotiation and variances based on the
     * value of those request headers.
     *
     * @param requestHeaders the request header names
     * @since 4.3
     */
    public void setVary(List<String> requestHeaders) {
        set(VARY, toCommaDelimitedString(requestHeaders));
    }

    /**
     * Return the request header names subject to content negotiation.
     *
     * @return TODO
     * @since 4.3
     */
    public List<String> getVary() {
        return getValuesAsList(VARY);
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param date       TODO
     * @param headerName TODO
     * @since 5.0
     */
    public void setZonedDateTime(String headerName, ZonedDateTime date) {
        set(headerName, DATE_FORMATTER.format(date));
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param headerName TODO
     * @param date       TODO
     * @since 5.1.4
     */
    public void setInstant(String headerName, Instant date) {
        setZonedDateTime(headerName, ZonedDateTime.ofInstant(date, GMT));
    }

    /**
     * Set the given date under the given header name after formatting it as a string
     * using the RFC-1123 date-time formatter. The equivalent of
     * {@link #set(String, String)} but for date headers.
     *
     * @param headerName TODO
     * @param date       TODO
     * @see #setZonedDateTime(String, ZonedDateTime)
     * @since 3.2.4
     */
    public void setDate(String headerName, long date) {
        setInstant(headerName, Instant.ofEpochMilli(date));
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return -1 if there is no value, or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName the header name
     * @return the parsed date header, or -1 if none
     * @see #getFirstZonedDateTime(String)
     * @since 3.2.4
     */
    public long getFirstDate(String headerName) {
        return getFirstDate(headerName, true);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return -1 if there is no value or also in case of an invalid value
     * (if {@code rejectInvalid=false}), or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName    the header name
     * @param rejectInvalid whether to reject invalid values with an
     *                      {@link IllegalArgumentException} ({@code true}) or rather return -1
     *                      in that case ({@code false})
     * @return the parsed date header, or -1 if none (or invalid)
     * @see #getFirstZonedDateTime(String, boolean)
     */
    private long getFirstDate(String headerName, boolean rejectInvalid) {
        ZonedDateTime zonedDateTime = getFirstZonedDateTime(headerName, rejectInvalid);
        return (zonedDateTime != null ? zonedDateTime.toInstant().toEpochMilli() : -1);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return {@code null} if there is no value, or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName the header name
     * @return the parsed date header, or {@code null} if none
     * @since 5.0
     */
    public ZonedDateTime getFirstZonedDateTime(String headerName) {
        return getFirstZonedDateTime(headerName, true);
    }

    /**
     * Parse the first header value for the given header name as a date,
     * return {@code null} if there is no value or also in case of an invalid value
     * (if {@code rejectInvalid=false}), or raise {@link IllegalArgumentException}
     * if the value cannot be parsed as a date.
     *
     * @param headerName    the header name
     * @param rejectInvalid whether to reject invalid values with an
     *                      {@link IllegalArgumentException} ({@code true}) or rather return {@code null}
     *                      in that case ({@code false})
     * @return the parsed date header, or {@code null} if none (or invalid)
     */
    private ZonedDateTime getFirstZonedDateTime(String headerName, boolean rejectInvalid) {
        String headerValue = getFirst(headerName);
        if (headerValue == null) {
            // No header value sent at all
            return null;
        }
        if (headerValue.length() >= 3) {
            // Short "0" or "-1" like values are never valid HTTP date headers...
            // Let's only bother with DateTimeFormatter parsing for long enough values.

            // See https://stackoverflow.com/questions/12626699/if-modified-since-http-header-passed-by-ie9-includes-length
            int parametersIndex = headerValue.indexOf(';');
            if (parametersIndex != -1) {
                headerValue = headerValue.substring(0, parametersIndex);
            }

            for (DateTimeFormatter dateFormatter : DATE_PARSERS) {
                try {
                    return ZonedDateTime.parse(headerValue, dateFormatter);
                } catch (DateTimeParseException ex) {
                    // ignore
                }
            }

        }
        if (rejectInvalid) {
            throw new IllegalArgumentException("Cannot parse date value \"" + headerValue +
                    "\" for \"" + headerName + "\" header");
        }
        return null;
    }

    /**
     * Return all values of a given header name, even if this header is set
     * multiple times.
     * <p>This method supports double-quoted values, as described in
     * <a href="https://www.rfc-editor.org/rfc/rfc9110.html#section-5.5-8">RFC
     * 9110, section 5.5</a>.
     *
     * @param headerName the header name
     * @return all associated values
     * @since 4.3
     */
    public List<String> getValuesAsList(String headerName) {
        Collection<String> values = get(headerName);
        if (values != null) {
            List<String> result = new ArrayList<>();
            for (String value : values) {
                if (value != null) {
                    result.addAll(tokenizeQuoted(value));
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    private static List<String> tokenizeQuoted(String str) {
        List<String> tokens = new ArrayList<>();
        boolean quoted = false;
        boolean trim = true;
        StringBuilder builder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch == '"') {
                if (CharSequenceUtil.isEmpty(builder)) {
                    quoted = true;
                } else if (quoted) {
                    quoted = false;
                    trim = false;
                } else {
                    builder.append(ch);
                }
            } else if (ch == '\\' && quoted && i < str.length() - 1) {
                builder.append(str.charAt(++i));
            } else if (ch == ',' && !quoted) {
                addToken(builder, tokens, trim);
                builder.setLength(0);
                trim = false;
            } else if (quoted || (!CharSequenceUtil.isNotEmpty(builder) && trim) || !Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        if (CharSequenceUtil.isNotEmpty(builder)) {
            addToken(builder, tokens, trim);
        }
        return tokens;
    }

    private static void addToken(StringBuilder builder, List<String> tokens, boolean trim) {
        String token = builder.toString();
        if (trim) {
            token = token.trim();
        }
        if (!token.isEmpty()) {
            tokens.add(token);
        }
    }

    /**
     * Remove the well-known {@code "Content-*"} HTTP headers.
     * <p>Such headers should be cleared from the response if the intended
     * body can't be written due to errors.
     *
     * @since 5.2.3
     */
    public void clearContentHeaders() {
        this.headers.remove(HttpHeader.CONTENT_DISPOSITION);
        this.headers.remove(HttpHeader.CONTENT_ENCODING);
        this.headers.remove(HttpHeader.CONTENT_LANGUAGE);
        this.headers.remove(HttpHeader.CONTENT_LENGTH);
        this.headers.remove(HttpHeader.CONTENT_LOCATION);
        this.headers.remove(HttpHeader.CONTENT_RANGE);
        this.headers.remove(HttpHeader.CONTENT_TYPE);
    }

    /**
     * Retrieve a combined result from the field values of the ETag header.
     *
     * @param headerName the header name
     * @return the combined result
     * @throws IllegalArgumentException if parsing fails
     * @since 4.3
     */
    protected List<String> getETagValuesAsList(String headerName) {
        Collection<String> values = get(headerName);
        if (values != null) {
            List<String> result = new ArrayList<>();
            for (String value : values) {
                if (value != null) {
                    Matcher matcher = ETAG_HEADER_VALUE_PATTERN.matcher(value);
                    while (matcher.find()) {
                        if ("*".equals(matcher.group())) {
                            result.add(matcher.group());
                        } else {
                            result.add(matcher.group(1));
                        }
                    }
                    if (result.isEmpty()) {
                        throw new IllegalArgumentException(
                                "Could not parse header '" + headerName + "' with value '" + value + "'");
                    }
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    /**
     * Retrieve a combined result from the field values of multivalued headers.
     *
     * @param headerName the header name
     * @return the combined result
     * @since 4.3
     */
    protected String getFieldValues(String headerName) {
        Collection<String> headerValues = get(headerName);
        return (headerValues != null ? toCommaDelimitedString(headerValues) : null);
    }

    /**
     * Turn the given list of header values into a comma-delimited result.
     *
     * @param headerValues the list of header values
     * @return a combined result with comma delimitation
     */
    protected String toCommaDelimitedString(Collection<String> headerValues) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String val : headerValues) {
            if (val != null) {
                joiner.add(val);
            }
        }
        return joiner.toString();
    }

    /**
     * Set the given header value, or remove the header if {@code null}.
     *
     * @param headerName  the header name
     * @param headerValue the header value, or {@code null} for none
     */
    private void setOrRemove(String headerName, String headerValue) {
        if (headerValue != null) {
            set(headerName, headerValue);
        } else {
            remove(headerName);
        }
    }


    // MultiValueMap implementation

    /**
     * Return the first header value for the given header name, if any.
     *
     * @param headerName the header name
     * @return the first header value, or {@code null} if none
     */
    public String getFirst(String headerName) {
        Collection<String> values = this.headers.get(headerName);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.iterator().next();
    }

    /**
     * Encode the given username and password into Basic Authentication credentials.
     * <p>The encoded credentials returned by this method can be supplied to
     * {@link #setBasicAuth(String)} to set the Basic Authentication header.
     *
     * @param username the username
     * @param password the password
     * @param charset  the charset to use to convert the credentials into an octet
     *                 sequence. Defaults to {@linkplain StandardCharsets#ISO_8859_1 ISO-8859-1}.
     * @return TODO
     * @throws IllegalArgumentException if {@code username} or {@code password}
     *                                  contains characters that cannot be encoded to the given charset
     * @see #setBasicAuth(String)
     * @see #setBasicAuth(String, String)
     * @see #setBasicAuth(String, String, Charset)
     * @see <a href="https://tools.ietf.org/html/rfc7617">RFC 7617</a>
     * @since 5.2
     */
    public static String encodeBasicAuth(String username, String password, Charset charset) {
        Assert.notNull(username, "Username must not be null");
        Assert.notContain(username, ":", "Username must not contain a colon");
        Assert.notNull(password, "Password must not be null");
        if (charset == null) {
            charset = StandardCharsets.ISO_8859_1;
        }

        CharsetEncoder encoder = charset.newEncoder();
        if (!encoder.canEncode(username) || !encoder.canEncode(password)) {
            throw new IllegalArgumentException(
                    "Username or password contains characters that cannot be encoded to " + charset.displayName());
        }

        String credentialsString = username + ":" + password;
        byte[] encodedBytes = Base64.getEncoder().encode(credentialsString.getBytes(charset));
        return new String(encodedBytes, charset);
    }

    // region extension function for MultiValuedMap

    /**
     * TODO
     *
     * @param headerName TODO
     * @param value      TODO
     */
    public void set(String headerName, String value) {
        this.headers.put(headerName, value);
    }
    // endregion

    // region MultiValuedMap delegate function
    @Override
    public int size() {
        return headers.size();
    }

    @Override
    public boolean isEmpty() {
        return headers.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return headers.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return headers.containsValue(value);
    }

    @Override
    public boolean containsMapping(Object key, Object value) {
        return headers.containsMapping(key, value);
    }

    @Override
    public Collection<String> get(String key) {
        return headers.get(key);
    }

    @Override
    public boolean put(String key, String value) {
        return headers.put(key, value);
    }

    @Override
    public boolean putAll(String key, Iterable<? extends String> values) {
        return headers.putAll(key, values);
    }

    @Override
    public boolean putAll(Map<? extends String, ? extends String> map) {
        return headers.putAll(map);
    }

    @Override
    public boolean putAll(MultiValuedMap<? extends String, ? extends String> map) {
        return headers.putAll(map);
    }

    @Override
    public Collection<String> remove(Object key) {
        return headers.remove(key);
    }

    @Override
    public boolean removeMapping(Object key, Object item) {
        return headers.removeMapping(key, item);
    }

    @Override
    public void clear() {
        headers.clear();
    }

    @Override
    public Collection<Map.Entry<String, String>> entries() {
        return headers.entries();
    }

    @Override
    public MultiSet<String> keys() {
        return headers.keys();
    }

    @Override
    public Set<String> keySet() {
        return headers.keySet();
    }

    @Override
    public Collection<String> values() {
        return headers.values();
    }

    @Override
    public Map<String, Collection<String>> asMap() {
        return headers.asMap();
    }

    @Override
    public MapIterator<String, String> mapIterator() {
        return headers.mapIterator();
    }
    // endregion
}
