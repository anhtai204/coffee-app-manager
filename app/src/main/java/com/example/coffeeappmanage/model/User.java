package com.example.coffeeappmanage.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id_user;
    private String email;
    private String password;
    private int id_role;

    public User(int id_user, String email, String password, int id_role) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.id_role = id_role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + id_user +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", id_role=" + id_role +
                '}';
    }
}
