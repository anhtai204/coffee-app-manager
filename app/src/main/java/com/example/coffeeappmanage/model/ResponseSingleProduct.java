package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class ResponseSingleProduct implements Serializable {
    private Product_1 data;
    private int statusCode;
    private String message;

    public ResponseSingleProduct(){}

    public ResponseSingleProduct(Product_1 data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Product_1 getData() {
        return data;
    }

    public void setData(Product_1 data) {
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
        return "ResponseProduct_1{" +
                "data=" + data +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}
