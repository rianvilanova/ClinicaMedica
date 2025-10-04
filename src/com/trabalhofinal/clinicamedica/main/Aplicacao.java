package com.trabalhofinal.clinicamedica.main;

import com.trabalhofinal.clinicamedica.service.AgendamentoService;
import com.trabalhofinal.clinicamedica.service.GerenciadorDeDados;
import com.trabalhofinal.clinicamedica.service.UsuarioService;
import com.trabalhofinal.clinicamedica.view.TelaLogin;

public class Aplicacao {
    public static void main(String[] args) {
        GerenciadorDeDados gerenciadorDeDados = new GerenciadorDeDados();
        UsuarioService usuarioService = new UsuarioService(gerenciadorDeDados);
        AgendamentoService agendamentoService = new AgendamentoService(gerenciadorDeDados);

        
        javax.swing.SwingUtilities.invokeLater(() -> {
            new TelaLogin(usuarioService, agendamentoService).setVisible(true);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(gerenciadorDeDados::salvarDados));
    }
}