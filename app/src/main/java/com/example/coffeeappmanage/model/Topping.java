package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class Topping implements Serializable {
    private int id_topping;
    private String topping_name;
    private float giaTopping;

    public Topping() {
    }

    public Topping(int id_topping, String topping_name, float giaTopping) {
        this.id_topping = id_topping;
        this.topping_name = topping_name;
        this.giaTopping = giaTopping;
    }

    public int getId_topping() {
        return id_topping;
    }

    public void setId_topping(int id_topping) {
        this.id_topping = id_topping;
    }

    public String getTopping_name() {
        return topping_name;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }

    public float getGiaTopping() {
        return giaTopping;
    }

    public void setGiaTopping(float giaTopping) {
        this.giaTopping = giaTopping;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "id_topping=" + id_topping +
                ", topping_name='" + topping_name + '\'' +
                ", giaTopping=" + giaTopping +
                '}';
    }
}
