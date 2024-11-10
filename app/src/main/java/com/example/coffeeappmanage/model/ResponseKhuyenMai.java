package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseKhuyenMai implements Serializable {
    private List<KhuyenMai> data;
    private int statusCode;
    private String message;

    public ResponseKhuyenMai(){}

    public ResponseKhuyenMai(List<KhuyenMai> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<KhuyenMai> getData() {
        return data;
    }

    public void setData(List<KhuyenMai> data) {
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
        return "ResponseKhuyenMai{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
