package com.example.restaurantapp.models;

import java.util.Date;

public class User {

    public String name;
    public String email;
    public String address = null;
    public int cellphone = 0;
    public String birthdate = null;


    public User() {
    }

    public User(String name, String email, String address, int cellphone, String birthdate) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.cellphone = cellphone;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
