package org.tenio.interstellar.service.http.client;

import org.apache.commons.collections4.MultiValuedMap;
import org.tenio.interstellar.service.http.HttpHeader;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: Response
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 10:39
 * &#064;version: 1.0
 */
public class Response {
    private int status;
    private String statusMessage;
    private HttpHeader httpHeader;
    private Object body;

    /**
     * TODO
     */
    public Response() {
        httpHeader = new HttpHeader();
    }

    /**
     * TODO
     *
     * @param headers TODO
     */
    public Response(MultiValuedMap<String, String> headers) {
        this.httpHeader = new HttpHeader(headers);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public int getStatus() {
        return status;
    }

    /**
     * TODO
     *
     * @param status TODO
     * @return TODO
     */
    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * TODO
     *
     * @param statusMessage TODO
     * @return TODO
     */
    public Response setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    /**
     * TODO
     *
     * @param tClass TODO
     * @return TODO
     */
    public boolean bodyIs(Class tClass) {
        return tClass.isInstance(body);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Object getBody() {
        return body;
    }

    /**
     * TODO
     *
     * @param tClass TODO
     * @param <T>    TODO
     * @return TODO
     */
    public <T> T getBody(Class<T> tClass) {
        return tClass.cast(body);
    }

    /**
     * TODO
     *
     * @param body TODO
     * @return TODO
     */
    public Response setBody(Object body) {
        this.body = body;
        return this;
    }
}
