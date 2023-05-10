package org.tenio.interstellar.service.http.client;

import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.tenio.interstellar.service.http.HttpHeader;
import org.tenio.interstellar.service.http.HttpMethod;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 基础Request构建对象
 *
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: RequestBuilder
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:28
 * @version: 1.0
 */
public abstract class RequestBuilder<T extends RequestBuilder<T>> {
    private String host;
    private int port = 80;
    private HttpMethod method;
    private String path;
    private String anchor;
    private final HttpHeader httpHeaders = new HttpHeader();
    private final MultiValuedMap<String, String> queryString = new HashSetValuedHashMap<>();
    private final T t;
    private Object body;

    /**
     * 空的构造器
     */
    public RequestBuilder() {
        this.t = (T) this;
    }

    /**
     * 空的构造器
     */
    public RequestBuilder(String method) {
        this(HttpMethod.valueOf(method));
    }

    public RequestBuilder(HttpMethod method) {
        this.method = method;
        this.t = (T) this;
    }

    // region Base http setting

    public boolean hasBody() {
        return false;
    }

    public Object getBody() {
        return body;
    }

    public T setBody(Object body) {
        this.body = body;
        return t;
    }

    /**
     * 获取Builder设置的Host
     *
     * @return Host地址
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置请求的Host地址
     *
     * @param host 请求的主机地址
     * @return 当前Builder对象
     */
    public T setHost(String host) {
        this.host = host;
        return t;
    }

    /**
     * 获取设置的端口号，默认是80
     *
     * @return 请求端口号
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置请求的端口号，默认是80
     *
     * @param port 请求端口号
     * @return 当前Builder对象
     */
    public T setPort(int port) {
        this.port = port;
        return t;
    }

    /**
     * 获取请求使用的Method
     *
     * @return 请求的Method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * 设置请求的Method方法
     *
     * @param method 请求的Method
     * @return 当前Builder对象
     */
    public T setMethod(String method) {
        return setMethod(HttpMethod.valueOf(method));
    }

    /**
     * 设置请求的Method方法
     *
     * @param method 请求的Method
     * @return 当前Builder对象
     */
    public T setMethod(HttpMethod method) {
        this.method = method;
        return t;
    }

    /**
     * 获取请求路径
     *
     * @return 请求路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置请求路径
     *
     * @param path 请求路径
     * @return 当前Builder对象
     */
    public T setPath(String path) {
        this.path = path;
        return t;
    }

    /**
     * 获取请求锚点
     *
     * @return 请求锚点
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * 设置请求锚点
     *
     * @param anchor 请求锚点
     * @return 当前Builder对象
     */
    public T setAnchor(String anchor) {
        this.anchor = anchor;
        return t;
    }

    // endregion

    // region header functions

    /**
     * 设置的Header数量
     *
     * @return Header数量
     */
    public int headerSize() {
        return httpHeaders.size();
    }

    /**
     * 设置的Header是否为空
     *
     * @return 是否为空
     */
    public boolean headerIsEmpty() {
        return httpHeaders.isEmpty();
    }

    /**
     * 设置的Header是否包含指定的Key
     *
     * @param key 查询的Key
     * @return 是否包含指定Key
     */
    public boolean headerContainsKey(Object key) {
        return httpHeaders.containsKey(key);
    }

    /**
     * 获取指定Header Key的所有值
     *
     * @param key 查询的Key
     * @return 设置的Value集合
     */
    public Collection<String> getHeaders(String key) {
        return httpHeaders.get(key);
    }

    /**
     * 获取指定Header Key的第一个值
     *
     * @param key 查询的Key
     * @return 设置Header Key的第一个值
     */
    public String getHeader(String key) {
        Collection<String> headers = getHeaders(key);
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        return headers.iterator().next();
    }

    /**
     * 添加Header Key值
     *
     * @param key   指定Key
     * @param value 设置的Header值
     * @return 当前Builder对象
     */
    public T putHeader(String key, String value) {
        httpHeaders.put(key, value);
        return t;
    }

    /**
     * 添加多个Header Key值
     *
     * @param map 设置的Header集合
     * @return 当前Builder对象
     */
    public T putAllHeaders(Map<? extends String, ? extends String> map) {
        httpHeaders.putAll(map);
        return t;
    }

    /**
     * 删除指定的Key
     *
     * @param key 删除的Key
     * @return 当前Builder对象
     */
    public T removeHeader(Object key) {
        httpHeaders.remove(key);
        return t;
    }

    /**
     * 获取所有设置Header的Key集合
     *
     * @return 所有Key集合
     */
    public MultiSet<String> headerKeys() {
        return httpHeaders.keys();
    }


    /**
     * 获取所有设置Header的Key集合
     *
     * @return 所有Key集合
     */
    public Set<String> headerKeySet() {
        return httpHeaders.keySet();
    }

    /**
     * 获取设置Header的Value集合
     *
     * @return 所有Value集合
     */
    public Collection<String> headerValues() {
        return httpHeaders.values();
    }

    /**
     * 将设置的Header转换成多值Map
     *
     * @return 所有Header的Map集合
     */
    public Map<String, Collection<String>> headerAsMap() {
        return httpHeaders.asMap();
    }

    /**
     * 清理所有设置的Header
     *
     * @return 当前Builder对象
     */
    public T headerClear() {
        httpHeaders.clear();
        return t;
    }

    /**
     * 获取当前HttpHeader
     *
     * @return HttpHeader
     */
    public HttpHeader getHttpHeader() {
        return httpHeaders;
    }
    // endregion

    // region queryString functions

    /**
     * 清理所有设置的queryString
     *
     * @return 当前Builder对象
     */
    public T qsClear() {
        httpHeaders.clear();
        return t;
    }

    /**
     * 获取所有设置queryString的Key集合
     *
     * @return queryString的Key集合
     */
    public MultiSet<String> qsKeys() {
        return queryString.keys();
    }


    /**
     * 获取所有设置queryString的Key集合
     *
     * @return queryString的Key集合
     */
    public Set<String> qsKeySet() {
        return queryString.keySet();
    }

    /**
     * 获取所有设置queryString的Value集合
     *
     * @return value集合
     */
    public Collection<String> qsValues() {
        return queryString.values();
    }

    /**
     * queryString转换为Map
     *
     * @return queryString的Map
     */
    public Map<String, Collection<String>> qsAsMap() {
        return queryString.asMap();
    }

    /**
     * 设置的queryString数量
     *
     * @return queryString数量
     */
    public int qsSize() {
        return queryString.size();
    }

    /**
     * queryString设置是否为空
     *
     * @return 是否为空
     */
    public boolean qsIsEmpty() {
        return queryString.isEmpty();
    }

    /**
     * queryString是否包含指定的参数Key
     *
     * @param key 查询的参数Key
     * @return 是否包含
     */
    public boolean qsContainsKey(Object key) {
        return queryString.containsKey(key);
    }

    /**
     * 获取指定queryString Key的所有值
     *
     * @param key 查询的Key
     * @return 所有指定Key的值集合
     */
    public Collection<String> getQss(String key) {
        return queryString.get(key);
    }

    /**
     * 获取指定queryString Key的第一个值
     *
     * @param key 查询的Key
     * @return 指定Key的第一个值
     */
    public String getQs(String key) {
        Collection<String> qss = getQss(key);
        if (qss == null || qss.isEmpty()) {
            return null;
        }
        return qss.iterator().next();
    }

    /**
     * 添加指定Key的值
     *
     * @param key   指定queryString的Key
     * @param value 添加的值
     * @return 当前Builder对象
     */
    public T putQs(String key, String value) {
        queryString.put(key, value);
        return t;
    }

    /**
     * 添加指定集合到queryString
     *
     * @param map 添加的queryString集合
     * @return 当前Builder对象
     */
    public T putAllQs(Map<? extends String, ? extends String> map) {
        queryString.putAll(map);
        return t;
    }

    /**
     * 删除指定queryString Key
     *
     * @param key 删除的Key
     * @return 当前Builder对象
     */
    public T removeQs(Object key) {
        queryString.remove(key);
        return t;
    }
    // endregion
}
