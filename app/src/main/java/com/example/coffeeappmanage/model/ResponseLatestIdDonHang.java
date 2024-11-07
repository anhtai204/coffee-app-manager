package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class ResponseLatestIdDonHang implements Serializable {
    private int data;
    private int statusCode;
    private String message;

    public ResponseLatestIdDonHang(){};

    public ResponseLatestIdDonHang(int data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
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
        return "ResponseLatestIdDonHang{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
