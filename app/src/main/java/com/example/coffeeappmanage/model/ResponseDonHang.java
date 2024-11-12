package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseDonHang implements Serializable {
    private List<DonHang> data;
    private int statusCode;
    private String message;

    public ResponseDonHang(){}

    public ResponseDonHang(List<DonHang> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<DonHang> getData() {
        return data;
    }

    public void setData(List<DonHang> data) {
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
        return "ResponseDonHang{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
