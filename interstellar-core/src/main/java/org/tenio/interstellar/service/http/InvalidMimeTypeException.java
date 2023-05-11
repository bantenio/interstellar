package org.tenio.interstellar.service.http;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: InvalidMimeTypeException
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 14:07
 * &#064;version: 1.0
 */
public class InvalidMimeTypeException extends IllegalArgumentException {
    /**
     * MimeType名称
     */
    private final String mimeType;


    /**
     * Create a new InvalidContentTypeException for the given content type.
     *
     * @param mimeType the offending media type
     * @param message  a detail message indicating the invalid part
     */
    public InvalidMimeTypeException(String mimeType, String message) {
        super("Invalid mime type \"" + mimeType + "\": " + message);
        this.mimeType = mimeType;
    }


    /**
     * Return the offending content type.
     *
     * @return TODO
     */
    public String getMimeType() {
        return this.mimeType;
    }

}
