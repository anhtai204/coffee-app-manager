package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private int id_theLoai;
    private String ten_the_loai;

    public TheLoai() {
    }

    public TheLoai(int id_theLoai, String ten_the_loai) {
        this.id_theLoai = id_theLoai;
        this.ten_the_loai = ten_the_loai;
    }

    public int getId_theLoai() {
        return id_theLoai;
    }

    public void setId_theLoai(int id_theLoai) {
        this.id_theLoai = id_theLoai;
    }

    public String getTen_the_loai() {
        return ten_the_loai;
    }

    public void setTen_the_loai(String ten_the_loai) {
        this.ten_the_loai = ten_the_loai;
    }

    @Override
    public String toString() {
        return "TheLoai{" +
                "id_theLoai=" + id_theLoai +
                ", ten_the_loai='" + ten_the_loai + '\'' +
                '}';
    }
}
