package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class Product_1 implements Serializable {
    private int id_product;
    private String tenSanPham;
    private float giaSanPham;
    private float khuyenmai_gia;
    private String logo_product;
    private String mo_ta;
    private TheLoai theLoai;
    private KhuyenMai khuyenMai;

    public Product_1(){}

    public Product_1(int id_product, String tenSanPham, float giaSanPham, float khuyenmai_gia, String logo_product, String mo_ta, TheLoai theLoai, KhuyenMai khuyenMai) {
        this.id_product = id_product;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.khuyenmai_gia = khuyenmai_gia;
        this.logo_product = logo_product;
        this.mo_ta = mo_ta;
        this.theLoai = theLoai;
        this.khuyenMai = khuyenMai;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public float getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(float giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public float getKhuyenmai_gia() {
        return khuyenmai_gia;
    }

    public void setKhuyenmai_gia(float khuyenmai_gia) {
        this.khuyenmai_gia = khuyenmai_gia;
    }

    public String getLogo_product() {
        return logo_product;
    }

    public void setLogo_product(String logo_product) {
        this.logo_product = logo_product;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public TheLoai getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(TheLoai theLoai) {
        this.theLoai = theLoai;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    @Override
    public String toString() {
        return "Product_1{" +
                "id_product=" + id_product +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", giaSanPham=" + giaSanPham +
                ", khuyenmai_gia=" + khuyenmai_gia +
                ", logo_product='" + logo_product + '\'' +
                ", mo_ta='" + mo_ta + '\'' +
                ", theLoai=" + theLoai +
                ", khuyenMai=" + khuyenMai +
                '}';
    }
}
