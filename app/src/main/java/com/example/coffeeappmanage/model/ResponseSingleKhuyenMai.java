package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseSingleKhuyenMai implements Serializable {
    private KhuyenMai data;
    private int statusCode;
    private String message;

    public ResponseSingleKhuyenMai(){}

    public ResponseSingleKhuyenMai(KhuyenMai data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public KhuyenMai getData() {
        return data;
    }

    public void setData(KhuyenMai data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseSingleKhuyenMai{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
