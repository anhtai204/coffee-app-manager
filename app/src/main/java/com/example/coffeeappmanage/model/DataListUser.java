package com.example.coffeeappmanage.model;

import java.io.Serializable;
import java.util.List;

public class DataListUser implements Serializable {
    public List<User> listUser;

    public DataListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }
}
