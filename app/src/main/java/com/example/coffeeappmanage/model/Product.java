package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id_product;
    private String tenSanPham;
    private float giaSanPham;
    private float khuyenmai_gia;
    private TheLoai theLoai;
    private KhuyenMai khuyenMai;
    private Topping topping;
    private float average_star;

    public Product(int id_product, String tenSanPham, float giaSanPham, float khuyenmai_gia, TheLoai theLoai, KhuyenMai khuyenMai, Topping topping, float average_star) {
        this.id_product = id_product;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.khuyenmai_gia = khuyenmai_gia;
        this.theLoai = theLoai;
        this.khuyenMai = khuyenMai;
        this.topping = topping;
        this.average_star = average_star;
    }

    public Product() {
    }

    public float getAverage_star() {
        return average_star;
    }

    public void setAverage_star(float average_star) {
        this.average_star = average_star;
    }
//    public Product(int id_product, String tenSanPham, float giaSanPham, float khuyenmai_gia, TheLoai theLoai, KhuyenMai khuyenMai, Topping topping) {
//        this.id_product = id_product;
//        this.tenSanPham = tenSanPham;
//        this.giaSanPham = giaSanPham;
//        this.khuyenmai_gia = khuyenmai_gia;
//        this.theLoai = theLoai;
//        this.khuyenMai = khuyenMai;
//        this.topping = topping;
//    }

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

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", giaSanPham=" + giaSanPham +
                ", khuyenmai_gia=" + khuyenmai_gia +
                ", theLoai=" + theLoai +
                ", khuyenMai=" + khuyenMai +
                ", topping=" + topping +
                '}';
    }
}