package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseTheLoai implements Serializable {
    private List<TheLoai> data;
    private int statusCode;
    private String message;

    public ResponseTheLoai(){}

    public ResponseTheLoai(List<TheLoai> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<TheLoai> getData() {
        return data;
    }

    public void setData(List<TheLoai> data) {
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
        return "ResponseTheLoai{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
