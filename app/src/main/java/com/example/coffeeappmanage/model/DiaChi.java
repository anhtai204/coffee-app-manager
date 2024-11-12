package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class DiaChi implements Serializable {
    private int id_dia_chi;
    private int id_user;
    private String dia_chi;

    public DiaChi(){}

    public DiaChi(int id_dia_chi, int id_user, String dia_chi) {
        this.id_dia_chi = id_dia_chi;
        this.id_user = id_user;
        this.dia_chi = dia_chi;
    }

    public int getId_dia_chi() {
        return id_dia_chi;
    }

    public void setId_dia_chi(int id_dia_chi) {
        this.id_dia_chi = id_dia_chi;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    @Override
    public String toString() {
        return "DiaChi{" +
                "id_dia_chi=" + id_dia_chi +
                ", id_user=" + id_user +
                ", dia_chi='" + dia_chi + '\'' +
                '}';
    }
}
