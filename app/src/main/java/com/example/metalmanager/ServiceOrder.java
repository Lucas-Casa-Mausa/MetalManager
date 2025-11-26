package com.example.metalmanager;

public class ServiceOrder {
    private int id;
    private String status;
    private String descricaoServico;

    // Construtor vazio
    public ServiceOrder() {
    }

    // Construtor completo
    public ServiceOrder(int id, String status, String descricaoServico) {
        this.id = id;
        this.status = status;
        this.descricaoServico = descricaoServico;
    }

    // Construtor sem ID (para novos registros)
    public ServiceOrder(String status, String descricaoServico) {
        this.status = status;
        this.descricaoServico = descricaoServico;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    // Método para retornar descrição truncada (máximo 50 caracteres)
    public String getDescricaoTruncada() {
        if (descricaoServico != null && descricaoServico.length() > 50) {
            return descricaoServico.substring(0, 47) + "...";
        }
        return descricaoServico;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }
}