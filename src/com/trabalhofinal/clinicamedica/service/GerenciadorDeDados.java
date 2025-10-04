package com.trabalhofinal.clinicamedica.service;

import com.trabalhofinal.clinicamedica.dao.ConsultaDAO;
import com.trabalhofinal.clinicamedica.dao.MedicoDAO;
import com.trabalhofinal.clinicamedica.dao.PacienteDAO;
import com.trabalhofinal.clinicamedica.model.Consulta;
import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.Paciente;

import java.util.ArrayList;
import java.util.List;

// ...comentários removidos...
public class GerenciadorDeDados {

    
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Consulta> consultas;
    private List<Consulta> listaDeEspera;

    
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;
    private final ConsultaDAO consultaDAO;

    public GerenciadorDeDados() {
        this.medicoDAO = new MedicoDAO();
        this.pacienteDAO = new PacienteDAO();
        this.consultaDAO = new ConsultaDAO();
    this.listaDeEspera = new ArrayList<>(); 
        carregarDados();
    }

    
    public void carregarDados() {
        this.medicos = medicoDAO.carregar();
        this.pacientes = pacienteDAO.carregar();
        
        this.consultas = consultaDAO.carregar(this.medicos, this.pacientes);
        System.out.println("Dados carregados com sucesso!");
    }

    
    public void salvarDados() {
        medicoDAO.salvar(this.medicos);
        pacienteDAO.salvar(this.pacientes);
        consultaDAO.salvar(this.consultas);
        System.out.println("Dados salvos com sucesso!");
    }

    
    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Consulta> getListaDeEspera() {
        return listaDeEspera;
    }

    
    public void adicionarMedico(Medico medico) {
        
        int proximoId = medicos.stream().mapToInt(Medico::getId).max().orElse(0) + 1;
        
        this.medicos.add(medico);
    }

    public void adicionarConsulta(Consulta consulta){
        this.consultas.add(consulta);
    }
}