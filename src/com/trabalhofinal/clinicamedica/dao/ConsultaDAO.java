package com.trabalhofinal.clinicamedica.dao;

import com.trabalhofinal.clinicamedica.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsultaDAO {

    private static final String NOME_ARQUIVO = "consultas.csv";
    private static final String ARQUIVO_AVALIACOES = "avaliacoes.csv";

    public void salvar(List<Consulta> consultas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Consulta consulta : consultas) {
                
                String linha = consulta.getId() + ";" +
                        consulta.getData().toString() + ";" +
                        consulta.getStatus().name() + ";" +
                        consulta.getDescricao().replace(";", ",") + ";" +
                        consulta.getValorPago() + ";" +
                        consulta.getMedico().getId() + ";" +
                        consulta.getPaciente().getId();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
            e.printStackTrace();
        }

        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_AVALIACOES))) {
            for (Consulta consulta : consultas) {
                if (consulta.getAvaliacao() != null) {
                    
                    Avaliacao avaliacao = consulta.getAvaliacao();
                    String linha = consulta.getId() + ";" +
                            avaliacao.getEstrelas() + ";" +
                            avaliacao.getComentario().replace(";", ",");
                    bw.write(linha);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar avaliações: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public List<Consulta> carregar(List<Medico> medicos, List<Paciente> pacientes) {
        
        Map<Integer, Medico> mapaMedicos = medicos.stream().collect(Collectors.toMap(Medico::getId, Function.identity()));
        Map<Integer, Paciente> mapaPacientes = pacientes.stream().collect(Collectors.toMap(Paciente::getId, Function.identity()));

        List<Consulta> consultas = new ArrayList<>();

        
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    int id = Integer.parseInt(dados[0]);
                    LocalDate data = LocalDate.parse(dados[1]);
                    StatusConsulta status = StatusConsulta.valueOf(dados[2]);
                    String descricao = dados[3];
                    double valorPago = Double.parseDouble(dados[4]);
                    int medicoId = Integer.parseInt(dados[5]);
                    int pacienteId = Integer.parseInt(dados[6]);

                    Medico medico = mapaMedicos.get(medicoId);
                    Paciente paciente = mapaPacientes.get(pacienteId);

                    if (medico != null && paciente != null) {
                        Consulta consulta = new Consulta(id, data, medico, paciente);
                        consulta.setStatus(status);
                        consulta.setDescricao(descricao);
                        consulta.setValorPago(valorPago);
                        consultas.add(consulta);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Arquivo de consultas não encontrado. " + e.getMessage());
        }

        
        carregarAvaliacoes(consultas);

        return consultas;
    }

    private void carregarAvaliacoes(List<Consulta> consultas) {
        Map<Integer, Consulta> mapaConsultas = consultas.stream().collect(Collectors.toMap(Consulta::getId, Function.identity()));

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_AVALIACOES))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    int consultaId = Integer.parseInt(dados[0]);
                    int estrelas = Integer.parseInt(dados[1]);
                    String comentario = dados[2];

                    Consulta consulta = mapaConsultas.get(consultaId);
                    if (consulta != null) {
                        Avaliacao avaliacao = new Avaliacao(estrelas, comentario);
                        consulta.setAvaliacao(avaliacao);
                        
                        consulta.getMedico().adicionarAvaliacao(avaliacao);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Arquivo de avaliações não encontrado. " + e.getMessage());
        }
    }
}