package com.example.restaurantapp.models;

public class User {

    public String name;
    public String email;
    public String address = null;
    public String cellphone = null;


    public User() {
    }

    public User(String name, String email, String address, String cellphone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.cellphone = cellphone;
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}