package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Avaliacao;
import com.trabalhofinal.clinicamedica.model.Consulta;
import javax.swing.*;
import java.awt.*;

public class TelaAvaliacao extends JDialog {

    private final Consulta consulta;
    private JSpinner spinnerEstrelas;
    private JTextArea txtComentario;

    public TelaAvaliacao(JFrame owner, Consulta consulta) {
        super(owner, "Avaliar Consulta", true);
        this.consulta = consulta;
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));
        initComponents();
    }

    private void initComponents() {
        JPanel painelEstrelas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEstrelas.add(new JLabel("Sua nota (1 a 5 estrelas):"));
        spinnerEstrelas = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        painelEstrelas.add(spinnerEstrelas);
        add(painelEstrelas, BorderLayout.NORTH);

        txtComentario = new JTextArea();
        txtComentario.setLineWrap(true);
        add(new JScrollPane(txtComentario), BorderLayout.CENTER);

        JButton btnSalvar = new JButton("Salvar Avaliação");
        add(btnSalvar, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarAvaliacao());
    }

    private void salvarAvaliacao() {
        int estrelas = (int) spinnerEstrelas.getValue();
        String comentario = txtComentario.getText();

        Avaliacao novaAvaliacao = new Avaliacao(estrelas, comentario);
        consulta.setAvaliacao(novaAvaliacao);

        consulta.getMedico().adicionarAvaliacao(novaAvaliacao);

        JOptionPane.showMessageDialog(this, "Avaliação registrada. Obrigado!");
        this.dispose();
    }
}