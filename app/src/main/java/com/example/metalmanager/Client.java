package com.example.metalmanager;

public class Client {
    private int id;
    private String name;
    private String cnpj;
    private String phone;

    // Construtor vazio
    public Client() {
    }

    // Construtor completo
    public Client(int id, String name, String cnpj, String phone) {
        this.id = id;
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
    }

    // Construtor sem ID (para novos registros)
    public Client(String name, String cnpj, String phone) {
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getPhone() {
        return phone;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}