package com.example.metalmanager;

public class Client {
    private int id;
    private String name;
    private String cnpj;
    private String phone;

    public Client(int id, String name, String cnpj, String phone) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}