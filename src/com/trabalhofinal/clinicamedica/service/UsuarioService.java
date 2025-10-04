package com.trabalhofinal.clinicamedica.service;

import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.Paciente;

// ...comentários removidos...
public class UsuarioService {

    private final GerenciadorDeDados gerenciador;

    public UsuarioService(GerenciadorDeDados gerenciador) {
        this.gerenciador = gerenciador;
    }

    
    public Medico loginMedico(String usuario, String senha) {
        for (Medico medico : gerenciador.getMedicos()) {
            if (medico.getUsuario().equals(usuario) && medico.getSenha().equals(senha)) {
                return medico;
            }
        }
    return null; 
    }

    
    public Paciente loginPaciente(String usuario, String senha) {
        for (Paciente paciente : gerenciador.getPacientes()) {
            if (paciente.getUsuario().equals(usuario) && paciente.getSenha().equals(senha)) {
                return paciente;
            }
        }
    return null; 
    }

    
    public boolean alterarDadosMedico(Medico medico, String novoNome, String novaEspecialidade) {
        if (medico != null) {
            medico.setNome(novoNome);
            medico.setEspecialidade(novaEspecialidade);
            
            gerenciador.salvarDados();
            return true;
        }
        return false;
    }

    
    public boolean alterarDadosPaciente(Paciente paciente, String novoNome, int novaIdade) {
        if (paciente != null) {
            paciente.setNome(novoNome);
            paciente.setIdade(novaIdade);
            gerenciador.salvarDados();
            return true;
        }
        return false;
    }
}