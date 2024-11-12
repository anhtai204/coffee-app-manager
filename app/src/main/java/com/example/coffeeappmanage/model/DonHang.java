package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class DonHang implements Serializable {
    private int id_donHang;
    private int id_user;
    private int id_product;
    private int soLuong;
    private String tuyChinh;
    private int id_khuyenMai;
    private String status;
    private float giaDonHang;
    private String ghiChu;
    private int id_phuong_thuc_thanh_toan;
    private String diaChi;

    public DonHang(){}

    public DonHang(int id_donHang, int id_user, int id_product, int soLuong, String tuyChinh, int id_khuyenMai, String status, float giaDonHang, String ghiChu, int id_phuong_thuc_thanh_toan, String diaChi) {
        this.id_donHang = id_donHang;
        this.id_user = id_user;
        this.id_product = id_product;
        this.soLuong = soLuong;
        this.tuyChinh = tuyChinh;
        this.id_khuyenMai = id_khuyenMai;
        this.status = status;
        this.giaDonHang = giaDonHang;
        this.ghiChu = ghiChu;
        this.id_phuong_thuc_thanh_toan = id_phuong_thuc_thanh_toan;
        this.diaChi = diaChi;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getId_khuyenMai() {
        return id_khuyenMai;
    }

    public void setId_khuyenMai(int id_khuyenMai) {
        this.id_khuyenMai = id_khuyenMai;
    }

    public int getId_phuong_thuc_thanh_toan() {
        return id_phuong_thuc_thanh_toan;
    }

    public void setId_phuong_thuc_thanh_toan(int id_phuong_thuc_thanh_toan) {
        this.id_phuong_thuc_thanh_toan = id_phuong_thuc_thanh_toan;
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
                "id_donHang=" + id_donHang +
                ", id_user=" + id_user +
                ", id_product=" + id_product +
                ", soLuong=" + soLuong +
                ", tuyChinh='" + tuyChinh + '\'' +
                ", id_khuyenMai=" + id_khuyenMai +
                ", status='" + status + '\'' +
                ", giaDonHang=" + giaDonHang +
                ", ghiChu='" + ghiChu + '\'' +
                ", id_phuong_thuc_thanh_toan=" + id_phuong_thuc_thanh_toan +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
