package com.trabalhofinal.clinicamedica.model;

public class Paciente {
    private int id;
    private String nome;
    private int idade;
    private String planoDeSaude;
    private String usuario;
    private String senha;

    public Paciente(int id, String nome, int idade, String planoDeSaude, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.planoDeSaude = planoDeSaude;
        this.usuario = usuario;
        this.senha = senha;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getPlanoDeSaude() { return planoDeSaude; }
    public void setPlanoDeSaude(String planoDeSaude) { this.planoDeSaude = planoDeSaude; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}