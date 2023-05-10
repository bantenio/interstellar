package org.tenio.interstellar.service.http;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: InvalidMimeTypeException
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 14:07
 * @version: 1.0
 */
public class InvalidMimeTypeException extends IllegalArgumentException {

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
     */
    public String getMimeType() {
        return this.mimeType;
    }

}
