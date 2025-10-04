package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.Paciente;
import com.trabalhofinal.clinicamedica.service.AgendamentoService;
import com.trabalhofinal.clinicamedica.service.UsuarioService;
import javax.swing.*;
import java.awt.event.ActionEvent;

// ...comentários removidos...
public class TelaLogin extends JFrame {

    
    private final UsuarioService usuarioService;
    private final AgendamentoService agendamentoService;

    
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JRadioButton rdbPaciente;
    private JRadioButton rdbMedico;
    private JButton btnEntrar;

    public TelaLogin(UsuarioService usuarioService, AgendamentoService agendamentoService) {
        this.usuarioService = usuarioService;
        this.agendamentoService = agendamentoService;

        setTitle("Login - Sistema de Clínica Médica");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); 

    initComponents(); 
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(50, 30, 80, 25);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(130, 30, 200, 25);
        panel.add(txtUsuario);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 70, 80, 25);
        panel.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(130, 70, 200, 25);
        panel.add(txtSenha);

        rdbPaciente = new JRadioButton("Paciente", true);
        rdbPaciente.setBounds(130, 110, 80, 25);
        panel.add(rdbPaciente);

        rdbMedico = new JRadioButton("Médico");
        rdbMedico.setBounds(220, 110, 80, 25);
        panel.add(rdbMedico);

        ButtonGroup group = new ButtonGroup();
        group.add(rdbPaciente);
        group.add(rdbMedico);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(150, 160, 100, 30);
        panel.add(btnEntrar);

        add(panel);

        btnEntrar.addActionListener(this::realizarLogin);
    }

    private void realizarLogin(ActionEvent e) {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuário e senha devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rdbPaciente.isSelected()) {
            Paciente pacienteLogado = usuarioService.loginPaciente(usuario, senha);
            if (pacienteLogado != null) {
                new PainelPaciente(pacienteLogado, agendamentoService).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha de paciente inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } else if (rdbMedico.isSelected()) {
            Medico medicoLogado = usuarioService.loginMedico(usuario, senha);
            if (medicoLogado != null) {
                new PainelMedico(medicoLogado, agendamentoService).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha de médico inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}