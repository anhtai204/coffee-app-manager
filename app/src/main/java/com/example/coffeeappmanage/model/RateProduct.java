package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class RateProduct implements Serializable {
    private int id_product;
    private String ten_the_loai;
    private String tenSanPham;
    private float giaSanPham;
    private float khuyenmai_gia;
    private float average_rating;

    public RateProduct() {
    }

    public RateProduct(int id_product, String tenSanPham, float giaSanPham, float khuyenmai_gia, float average_rating, String ten_the_loai) {
        this.id_product = id_product;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.khuyenmai_gia = khuyenmai_gia;
        this.average_rating = average_rating;
        this.ten_the_loai = ten_the_loai;
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

    public float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(float average_rating) {
        this.average_rating = average_rating;
    }

    public String getTen_the_loai() {
        return ten_the_loai;
    }

    public void setTen_the_loai(String ten_the_loai) {
        this.ten_the_loai = ten_the_loai;
    }

    @Override
    public String toString() {
        return "RateProduct{" +
                "id_product=" + id_product +
                ", ten_the_loai='" + ten_the_loai + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", giaSanPham=" + giaSanPham +
                ", khuyenmai_gia=" + khuyenmai_gia +
                ", average_rating=" + average_rating +
                '}';
    }
}
