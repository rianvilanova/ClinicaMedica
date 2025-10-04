package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Consulta;
import com.trabalhofinal.clinicamedica.model.Medico;
import com.trabalhofinal.clinicamedica.model.StatusConsulta;
import com.trabalhofinal.clinicamedica.service.AgendamentoService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

// ...comentários removidos...
public class PainelMedico extends JFrame {

    private final Medico medicoLogado;
    private final AgendamentoService agendamentoService;
    private JTable tabelaAgenda;
    private DefaultTableModel tableModel;

    public PainelMedico(Medico medico, AgendamentoService agendamentoService) {
        this.medicoLogado = medico;
        this.agendamentoService = agendamentoService;

        setTitle("Painel do Médico - Dr(a). " + medicoLogado.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        atualizarAgenda();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("Sua agenda para hoje (" + LocalDate.now() + ")", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID Consulta", "Nome do Paciente", "Idade", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaAgenda = new JTable(tableModel);
        add(new JScrollPane(tabelaAgenda), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRealizarConsulta = new JButton("Realizar Consulta Selecionada");
        painelBotoes.add(btnRealizarConsulta);
        add(painelBotoes, BorderLayout.SOUTH);

        btnRealizarConsulta.addActionListener(e -> abrirTelaRealizarConsulta());
    }

    private void atualizarAgenda() {
        tableModel.setRowCount(0);

        
        List<Consulta> agendaDoDia = agendamentoService.getConsultasPorMedicoEData(medicoLogado, LocalDate.now());

        for (Consulta consulta : agendaDoDia) {
            Object[] rowData = {
                    consulta.getId(),
                    consulta.getPaciente().getNome(),
                    consulta.getPaciente().getIdade(),
                    consulta.getStatus().toString()
            };
            tableModel.addRow(rowData);
        }
    }

    private void abrirTelaRealizarConsulta() {
        int selectedRow = tabelaAgenda.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta agendada para realizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int consultaId = (int) tableModel.getValueAt(selectedRow, 0);
        
        Consulta consulta = agendamentoService.getConsultaPorId(consultaId);

        if (consulta != null && consulta.getStatus() == StatusConsulta.AGENDADA) {
            TelaRealizarConsulta telaConsulta = new TelaRealizarConsulta(this, consulta);
            telaConsulta.setVisible(true);
            atualizarAgenda(); 
        } else {
            JOptionPane.showMessageDialog(this, "Apenas consultas com status 'AGENDADA' podem ser realizadas.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}