package com.trabalhofinal.clinicamedica.model;

public class Avaliacao {
    private int estrelas;
    private String comentario;

    public Avaliacao(int estrelas, String comentario) {
        this.estrelas = Math.max(1, Math.min(estrelas, 5));
        this.comentario = comentario;
    }
    public int getEstrelas() { return estrelas; }
    public void setEstrelas(int estrelas) { this.estrelas = estrelas; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}