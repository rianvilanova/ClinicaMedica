package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Consulta;
import com.trabalhofinal.clinicamedica.model.StatusConsulta;
import javax.swing.*;
import java.awt.*;

public class TelaRealizarConsulta extends JDialog {

    private final Consulta consulta;
    private JTextArea txtDescricao;

    public TelaRealizarConsulta(JFrame owner, Consulta consulta) {
        super(owner, "Realizar Consulta", true);
        this.consulta = consulta;
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel painelInfo = new JPanel(new GridLayout(2, 2, 5, 5));
        painelInfo.setBorder(BorderFactory.createTitledBorder(""));
        painelInfo.add(new JLabel("Paciente:"));
        painelInfo.add(new JLabel(consulta.getPaciente().getNome()));
        painelInfo.add(new JLabel("Data:"));
        painelInfo.add(new JLabel(consulta.getData().toString()));
        add(painelInfo, BorderLayout.NORTH);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        add(new JScrollPane(txtDescricao), BorderLayout.CENTER);

        JButton btnFinalizar = new JButton("Finalizar e Salvar Consulta");
        add(btnFinalizar, BorderLayout.SOUTH);

        btnFinalizar.addActionListener(e -> finalizarConsulta());
    }

    private void finalizarConsulta() {
        String descricao = txtDescricao.getText();
        if (descricao.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        consulta.setDescricao(descricao);
        consulta.setStatus(StatusConsulta.REALIZADA);

        if (consulta.getPaciente().getPlanoDeSaude().equals("não tenho")) {
            double valorConsulta = 250.00;
            consulta.setValorPago(valorConsulta);
            JOptionPane.showMessageDialog(this, "", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }

        this.dispose();
    }
}