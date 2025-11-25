package com.example.metalmanager;

public class ServiceOrder {
    private int id;
    private String status;
    private String descricaoServico;

    public ServiceOrder(int id, String status, String descricaoServico) {
        this.id = id;
        this.status = status;
        this.descricaoServico = descricaoServico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    // Método auxiliar para truncar descrição (máximo 40 caracteres)
    public String getDescricaoTruncada() {
        if (descricaoServico != null && descricaoServico.length() > 40) {
            return descricaoServico.substring(0, 40) + "...";
        }
        return descricaoServico;
    }
}