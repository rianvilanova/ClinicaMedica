package com.trabalhofinal.clinicamedica.service;

import com.trabalhofinal.clinicamedica.model.Consulta;
import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.Paciente;
import com.trabalhofinal.clinicamedica.model.StatusConsulta;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// ...comentários removidos...
public class AgendamentoService {

    private final GerenciadorDeDados gerenciador;
    private static final int LIMITE_CONSULTAS_DIARIAS = 3;

    public AgendamentoService(GerenciadorDeDados gerenciador) {
        this.gerenciador = gerenciador;
    }

    
    public List<Medico> buscarMedicos(Paciente paciente, String termoBusca) {
        List<Medico> medicosDisponiveis = gerenciador.getMedicos().stream()
                
                .filter(medico -> paciente.getPlanoDeSaude().equals("não tenho") ||
                        medico.getPlanosDeSaude().contains(paciente.getPlanoDeSaude()))
                
                .filter(medico -> termoBusca.isEmpty() ||
                        medico.getNome().toLowerCase().contains(termoBusca.toLowerCase()) ||
                        medico.getEspecialidade().toLowerCase().contains(termoBusca.toLowerCase()))
                .collect(Collectors.toList());

        return medicosDisponiveis;
    }

    
    public String agendarConsulta(Paciente paciente, Medico medico, LocalDate data) {
        long consultasNoDia = gerenciador.getConsultas().stream()
                .filter(c -> c.getMedico().getId() == medico.getId() && c.getData().equals(data) && c.getStatus() == StatusConsulta.AGENDADA)
                .count();

        
        if (consultasNoDia < LIMITE_CONSULTAS_DIARIAS) {
            int proximoId = gerenciador.getConsultas().stream().mapToInt(Consulta::getId).max().orElse(0) + 1;
            Consulta novaConsulta = new Consulta(proximoId, data, medico, paciente);
            gerenciador.adicionarConsulta(novaConsulta);
            gerenciador.salvarDados();
            return "AGENDADO";
        } else {
            
            int proximoId = gerenciador.getConsultas().stream().mapToInt(Consulta::getId).max().orElse(0) + 1;
            Consulta consultaEmEspera = new Consulta(proximoId, data, medico, paciente);
            gerenciador.getListaDeEspera().add(consultaEmEspera);
            
            return "LISTA_DE_ESPERA";
        }
    }

    
    public void cancelarAgendamento(Consulta consultaParaCancelar) {
        consultaParaCancelar.setStatus(StatusConsulta.CANCELADA);

        
        gerenciador.getListaDeEspera().stream()
                .filter(c -> c.getMedico().getId() == consultaParaCancelar.getMedico().getId() && c.getData().equals(consultaParaCancelar.getData()))
                .findFirst() 
                .ifPresent(novaConsulta -> {
                    novaConsulta.setStatus(StatusConsulta.AGENDADA); 
                    gerenciador.adicionarConsulta(novaConsulta); 
                    gerenciador.getListaDeEspera().remove(novaConsulta); 
                    System.out.println("Paciente " + novaConsulta.getPaciente().getNome() + " foi promovido da lista de espera.");
                });

        gerenciador.salvarDados();
    }

    public List<Consulta> getConsultasPorMedicoEData(Medico medico, LocalDate data) {
        return gerenciador.getConsultas().stream()
                .filter(c -> c.getMedico().getId() == medico.getId() && c.getData().equals(data))
                .collect(Collectors.toList());
    }

    
    public List<Consulta> getConsultasPorPaciente(Paciente paciente) {
        return gerenciador.getConsultas().stream()
                .filter(c -> c.getPaciente().getId() == paciente.getId())
                .collect(Collectors.toList());
    }

    
    public Consulta getConsultaPorId(int consultaId) {
        return gerenciador.getConsultas().stream()
                .filter(c -> c.getId() == consultaId)
                .findFirst()
                .orElse(null);
    }
}