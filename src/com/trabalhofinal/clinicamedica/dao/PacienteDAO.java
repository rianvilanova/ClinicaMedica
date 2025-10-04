package com.trabalhofinal.clinicamedica.dao;

import com.trabalhofinal.clinicamedica.model.Paciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PacienteDAO {

    private static final String NOME_ARQUIVO = "pacientes.csv";

    public void salvar(List<Paciente> pacientes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Paciente paciente : pacientes) {
                String linha = paciente.getId() + ";" +
                        paciente.getNome() + ";" +
                        paciente.getIdade() + ";" +
                        paciente.getPlanoDeSaude() + ";" +
                        paciente.getUsuario() + ";" +
                        paciente.getSenha();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Paciente> carregar() {
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 6) {
                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    int idade = Integer.parseInt(dados[2]);
                    String planoDeSaude = dados[3];
                    String usuario = dados[4];
                    String senha = dados[5];

                    Paciente paciente = new Paciente(id, nome, idade, planoDeSaude, usuario, senha);
                    pacientes.add(paciente);
                }
            }
        } catch (IOException e) {
            System.err.println("Arquivo de pacientes não encontrado, criando um novo. " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato de número ao carregar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}