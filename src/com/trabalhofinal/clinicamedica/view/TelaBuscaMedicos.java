package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.Paciente;
import com.trabalhofinal.clinicamedica.service.AgendamentoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

// ...comentários removidos...
public class TelaBuscaMedicos extends JDialog {

    private final Paciente pacienteLogado;
    private final AgendamentoService agendamentoService;
    private List<Medico> medicosEncontrados;

    private JTextField txtBusca;
    private JTable tabelaMedicos;
    private DefaultTableModel tableModel;

    public TelaBuscaMedicos(JFrame owner, Paciente paciente, AgendamentoService agendamentoService) {
        super(owner, "Buscar Médicos e Agendar", true);

        this.pacienteLogado = paciente;
        this.agendamentoService = agendamentoService;

        setSize(700, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Buscar por Nome ou Especialidade:"));
        txtBusca = new JTextField(20);
        painelBusca.add(txtBusca);
        JButton btnBuscar = new JButton("Buscar");
        painelBusca.add(btnBuscar);
        add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Especialidade", "Avaliação (Média)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaMedicos = new JTable(tableModel);
        add(new JScrollPane(tabelaMedicos), BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel();
        JButton btnAgendar = new JButton("Agendar com Médico Selecionado");
        painelAcoes.add(btnAgendar);
        add(painelAcoes, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarMedicos());
        btnAgendar.addActionListener(e -> agendarConsulta());
    }

    private void buscarMedicos() {
        String termoBusca = txtBusca.getText();
        medicosEncontrados = agendamentoService.buscarMedicos(pacienteLogado, termoBusca);
        tableModel.setRowCount(0);

        for (Medico medico : medicosEncontrados) {
            Object[] rowData = {
                    medico.getId(),
                    medico.getNome(),
                    medico.getEspecialidade(),
                    String.format(Locale.US, "%.1f estrelas", medico.getMediaEstrelas())
            };
            tableModel.addRow(rowData);
        }
    }

    private void agendarConsulta() {
        int selectedRow = tabelaMedicos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um médico na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int medicoId = (int) tableModel.getValueAt(selectedRow, 0);
        Medico medicoSelecionado = medicosEncontrados.stream()
                .filter(m -> m.getId() == medicoId)
                .findFirst()
                .orElse(null);

        if (medicoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Erro ao encontrar o médico selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dataStr = JOptionPane.showInputDialog(this, "Digite a data da consulta (formato: AAAA-MM-DD):");
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return;
        }

        try {
            LocalDate data = LocalDate.parse(dataStr);
            String resultado = agendamentoService.agendarConsulta(pacienteLogado, medicoSelecionado, data);

            if (resultado.equals("AGENDADO")) {
                JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso para " + dataStr + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else if (resultado.equals("LISTA_DE_ESPERA")) {
                JOptionPane.showMessageDialog(this, "O médico não tem horários para este dia.\nVocê foi adicionado à lista de espera.", "Lista de Espera", JOptionPane.INFORMATION_MESSAGE);
            }

            this.dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use AAAA-MM-DD.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}