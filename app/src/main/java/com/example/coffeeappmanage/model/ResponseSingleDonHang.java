package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseSingleDonHang implements Serializable {
    private DonHang data;
    private int statusCode;
    private String message;

    public ResponseSingleDonHang(){}

    @Override
    public String toString() {
        return "ResponseSingleDonHang{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }

    public DonHang getData() {
        return data;
    }

    public void setData(DonHang data) {
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

    public ResponseSingleDonHang(DonHang data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }
}
