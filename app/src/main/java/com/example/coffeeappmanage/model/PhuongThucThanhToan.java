package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class PhuongThucThanhToan implements Serializable {
    private int id_phuongThucThanhToan;
    private String hinhThucThanhToan;

    public PhuongThucThanhToan(){}

    public PhuongThucThanhToan(int id_phuongThucThanhToan, String hinhThucThanhToan) {
        this.id_phuongThucThanhToan = id_phuongThucThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public int getId_phuongThucThanhToan() {
        return id_phuongThucThanhToan;
    }

    public void setId_phuongThucThanhToan(int id_phuongThucThanhToan) {
        this.id_phuongThucThanhToan = id_phuongThucThanhToan;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    @Override
    public String toString() {
        return "PhuongThucThanhToan{" +
                "id_phuongThucThanhToan=" + id_phuongThucThanhToan +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                '}';
    }
}
