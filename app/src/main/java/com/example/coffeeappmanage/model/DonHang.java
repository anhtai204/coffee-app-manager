package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id_user;
    private int id_product;
    private int soLuong;
    private String tuyChinh;
    private String status;
    private float giaDonHang;
    private String ghiChu;
    private int id_donHang;

    public DonHang(){}

    public DonHang(int id_user, int id_product, int soLuong, String tuyChinh, String status, float giaDonHang, String ghiChu, int id_donHang) {
        this.id_user = id_user;
        this.id_product = id_product;
        this.soLuong = soLuong;
        this.tuyChinh = tuyChinh;
        this.status = status;
        this.giaDonHang = giaDonHang;
        this.ghiChu = ghiChu;
        this.id_donHang = id_donHang;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTuyChinh() {
        return tuyChinh;
    }

    public void setTuyChinh(String tuyChinh) {
        this.tuyChinh = tuyChinh;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getGiaDonHang() {
        return giaDonHang;
    }

    public void setGiaDonHang(float giaDonHang) {
        this.giaDonHang = giaDonHang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getId_donHang() {
        return id_donHang;
    }

    public void setId_donHang(int id_donHang) {
        this.id_donHang = id_donHang;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "id_user=" + id_user +
                ", id_product=" + id_product +
                ", soLuong=" + soLuong +
                ", tuyChinh='" + tuyChinh + '\'' +
                ", status='" + status + '\'' +
                ", giaDonHang=" + giaDonHang +
                ", ghiChu='" + ghiChu + '\'' +
                ", id_donHang=" + id_donHang +
                '}';
    }
}
