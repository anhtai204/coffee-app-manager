package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseTopping implements Serializable {
    private List<Topping> data;
    private int statusCode;
    private String message;

    public ResponseTopping() {
    }

    public ResponseTopping(List<Topping> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public List<Topping> getData() {
        return data;
    }

    public void setData(List<Topping> data) {
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
        return "ResponseTopping{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
