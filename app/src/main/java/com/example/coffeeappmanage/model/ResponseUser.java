package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseUser implements Serializable {
    private List<User> data;
    private int statusCode;
    private String message;

    public ResponseUser(List<User> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
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
