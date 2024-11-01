package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class ResponseRateProduct implements Serializable {
    private List<RateProduct> data;
    private int statusCode;
    private String message;

    public ResponseRateProduct() {
    }

    public List<RateProduct> getData() {
        return data;
    }

    public void setData(List<RateProduct> data) {
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

    public ResponseRateProduct(List<RateProduct> data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseRateProduct{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
