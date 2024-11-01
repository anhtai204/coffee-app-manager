package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private int id_theLoai;
    private String tenTheLoai;

    public TheLoai() {
    }

    public TheLoai(int id_theLoai, String tenTheLoai) {
        this.id_theLoai = id_theLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public int getId_theLoai() {
        return id_theLoai;
    }

    public void setId_theLoai(int id_theLoai) {
        this.id_theLoai = id_theLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    @Override
    public String toString() {
        return "TheLoai{" +
                "id_theLoai=" + id_theLoai +
                ", tenTheLoai='" + tenTheLoai + '\'' +
                '}';
    }
}
