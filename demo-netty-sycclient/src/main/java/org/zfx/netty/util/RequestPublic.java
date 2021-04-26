package org.zfx.netty.util;

import java.io.Serializable;

public class RequestPublic<T> implements Serializable {
    /**
     * 时间戳，单位毫秒
     * */
    private Long timestamp;

    /**
     * API协议版本，固定值：1.0
     * */
    private String version;

    /**
     * API输入参数签名结果
     * */
    private String sign;

    /**
     * 接口入参
     * */
    private T data;

    /**
     * 页码
     * */
    private int page;

    /**
     * 每页记录数
     * */
    private int size;

    public RequestPublic(Long timestamp, String version, String sign) {
        this.timestamp = timestamp;
        this.version = version;
        this.sign = sign;
    }

    public RequestPublic(Long timestamp, String version, String sign, Object data) {
        this.timestamp = timestamp;
        this.version = version;
        this.sign = sign;
        this.data = (T) data;
    }

    public RequestPublic() {

    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
