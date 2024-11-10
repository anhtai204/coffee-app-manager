package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseSingleTopping implements Serializable {
    private Topping data;
    private int statusCode;
    private String message;

    public ResponseSingleTopping(){}

    public ResponseSingleTopping(Topping data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Topping getData() {
        return data;
    }

    public void setData(Topping data) {
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
        return "ResponseSingleTopping{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
