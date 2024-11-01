package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseProduct implements Serializable {
    private List<Product> data;
    private int statusCode;
    private String message;

    public ResponseProduct() {
    }

    public ResponseProduct(List<Product> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
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
}
