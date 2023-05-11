package org.tenio.interstellar.service.http;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: InvalidMediaTypeException
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 14:26
 * &#064;version: 1.0
 */
public class InvalidMediaTypeException extends IllegalArgumentException {
    /**
     * MediaType名称
     */
    private final String mediaType;


    /**
     * Create a new InvalidMediaTypeException for the given media type.
     *
     * @param mediaType the offending media type
     * @param message   a detail message indicating the invalid part
     */
    public InvalidMediaTypeException(String mediaType, String message) {
        super("Invalid media type \"" + mediaType + "\": " + message);
        this.mediaType = mediaType;
    }

    /**
     * Constructor that allows wrapping {@link InvalidMimeTypeException}.
     */
    InvalidMediaTypeException(InvalidMimeTypeException ex) {
        super(ex.getMessage(), ex);
        this.mediaType = ex.getMimeType();
    }


    /**
     * Return the offending media type.
     *
     * @return media type
     */
    public String getMediaType() {
        return this.mediaType;
    }
}