package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class KhuyenMai implements Serializable {
    private  int id_khuyen_mai;
    private float phanTramKhuyenMai;
    private float donHangToiThieu;

    public KhuyenMai() {
    }

    public KhuyenMai(int id_khuyen_mai, float phanTramKhuyenMai, float donHangToiThieu) {
        this.id_khuyen_mai = id_khuyen_mai;
        this.phanTramKhuyenMai = phanTramKhuyenMai;
        this.donHangToiThieu = donHangToiThieu;
    }

    public int getId_khuyen_mai() {
        return id_khuyen_mai;
    }

    public void setId_khuyen_mai(int id_khuyen_mai) {
        this.id_khuyen_mai = id_khuyen_mai;
    }

    public float getPhanTramKhuyenMai() {
        return phanTramKhuyenMai;
    }

    public void setPhanTramKhuyenMai(float phanTramKhuyenMai) {
        this.phanTramKhuyenMai = phanTramKhuyenMai;
    }

    public float getDonHangToiThieu() {
        return donHangToiThieu;
    }

    public void setDonHangToiThieu(float donHangToiThieu) {
        this.donHangToiThieu = donHangToiThieu;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "id_khuyen_mai=" + id_khuyen_mai +
                ", phanTramKhuyenMai=" + phanTramKhuyenMai +
                ", donHangToiThieu=" + donHangToiThieu +
                '}';
    }
}
