package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseSingleTheLoai implements Serializable {
    private TheLoai data;
    private int statusCode;
    private String message;

    public ResponseSingleTheLoai(){}

    public ResponseSingleTheLoai(TheLoai data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public TheLoai getData() {
        return data;
    }

    public void setData(TheLoai data) {
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
        return "ResponseSingleTheLoai{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
