package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseSingleUser implements Serializable {
    private User data;
    private int statusCode;
    private String message;

    public ResponseSingleUser(){}

    public ResponseSingleUser(User data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseSingleUser{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
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
