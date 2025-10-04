package com.trabalhofinal.clinicamedica.model;

import java.util.ArrayList;
import java.util.List;

public class Medico {
    private int id;
    private String nome;
    private String especialidade;
    private List<String> planosDeSaude;
    private List<Avaliacao> avaliacoes;
    private String usuario;
    private String senha;

    public Medico(int id, String nome, String especialidade, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.usuario = usuario;
        this.senha = senha;
        this.planosDeSaude = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public List<String> getPlanosDeSaude() { return planosDeSaude; }
    public void setPlanosDeSaude(List<String> planosDeSaude) { this.planosDeSaude = planosDeSaude; }
    public List<Avaliacao> getAvaliacoes() { return avaliacoes; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public void adicionarPlano(String plano) { this.planosDeSaude.add(plano); }
    public void adicionarAvaliacao(Avaliacao avaliacao) { this.avaliacoes.add(avaliacao); }
    public double getMediaEstrelas() {
        if (avaliacoes.isEmpty()) { return 0.0; }
        return avaliacoes.stream().mapToInt(Avaliacao::getEstrelas).average().orElse(0.0);
    }
}