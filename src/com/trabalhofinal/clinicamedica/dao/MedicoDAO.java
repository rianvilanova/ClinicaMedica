package com.trabalhofinal.clinicamedica.dao;

import com.trabalhofinal.clinicamedica.model.Medico;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ...comentários removidos...
public class MedicoDAO {

    private static final String NOME_ARQUIVO = "medicos.csv";

    
    public void salvar(List<Medico> medicos) {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Medico medico : medicos) {
                
                String planos = String.join("|", medico.getPlanosDeSaude());
                String linha = medico.getId() + ";" +
                        medico.getNome() + ";" +
                        medico.getEspecialidade() + ";" +
                        medico.getUsuario() + ";" +
                        medico.getSenha() + ";" +
                        planos;
                bw.write(linha);
                bw.newLine(); 
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    
    public List<Medico> carregar() {
        List<Medico> medicos = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length >= 5) {
                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String especialidade = dados[2];
                    String usuario = dados[3];
                    String senha = dados[4];

                    Medico medico = new Medico(id, nome, especialidade, usuario, senha);

                    
                    if (dados.length > 5 && !dados[5].isEmpty()) {
                        List<String> planos = new ArrayList<>(Arrays.asList(dados[5].split("\\|")));
                        medico.setPlanosDeSaude(planos);
                    }

                    medicos.add(medico);
                }
            }
        } catch (IOException e) {
            System.err.println("Arquivo de médicos não encontrado, criando um novo. " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número (ID) ao carregar médicos: " + e.getMessage());
        }
        return medicos;
    }
}