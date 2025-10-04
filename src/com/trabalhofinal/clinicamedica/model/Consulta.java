package com.trabalhofinal.clinicamedica.model;

import java.time.LocalDate;

public class Consulta {
    private int id;
    private LocalDate data;
    private Medico medico;
    private Paciente paciente;
    private String descricao;
    private StatusConsulta status;
    private Avaliacao avaliacao;
    private double valorPago;

    public Consulta(int id, LocalDate data, Medico medico, Paciente paciente) {
        this.id = id;
        this.data = data;
        this.medico = medico;
        this.paciente = paciente;
        this.status = StatusConsulta.AGENDADA;
        this.descricao = "";
        this.avaliacao = null;
        this.valorPago = 0.0;
    }
    public int getId() { return id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Medico getMedico() { return medico; }
    public Paciente getPaciente() { return paciente; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }
    public Avaliacao getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Avaliacao avaliacao) { this.avaliacao = avaliacao; }
    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
}