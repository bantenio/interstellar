package org.tenio.interstellar.service.http.client;

import org.apache.commons.collections4.MultiValuedMap;
import org.tenio.interstellar.service.http.HttpHeader;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: Response
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 10:39
 * @version: 1.0
 */
public class Response {
    private int status;
    private String statusMessage;
    private HttpHeader httpHeader;
    private Object body;

    public Response() {
        httpHeader = new HttpHeader();
    }

    public Response(MultiValuedMap<String, String> headers) {
        this.httpHeader = new HttpHeader(headers);
    }

    public int getStatus() {
        return status;
    }

    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Response setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public boolean bodyIs(Class tClass) {
        return tClass.isInstance(body);
    }

    public Object getBody() {
        return body;
    }

    public <T> T getBody(Class<T> tClass) {
        return tClass.cast(body);
    }

    public Response setBody(Object body) {
        this.body = body;
        return this;
    }
}
