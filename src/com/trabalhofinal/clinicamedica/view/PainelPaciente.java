package com.trabalhofinal.clinicamedica.view;

import com.trabalhofinal.clinicamedica.model.Consulta;
import com.trabalhofinal.clinicamedica.model.Paciente;
import com.trabalhofinal.clinicamedica.model.StatusConsulta;
import com.trabalhofinal.clinicamedica.service.AgendamentoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// ...comentários removidos...
public class PainelPaciente extends JFrame {

    private final Paciente pacienteLogado;
    private final AgendamentoService agendamentoService;

    private DefaultTableModel tableModel;
    private JTable tabelaConsultas;

    public PainelPaciente(Paciente paciente, AgendamentoService agendamentoService) {
        this.pacienteLogado = paciente;
        this.agendamentoService = agendamentoService;

        setTitle("Painel do Paciente - Clínica Médica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        atualizarTabelaConsultas();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBoasVindas = new JLabel("Bem-vindo(a), " + pacienteLogado.getNome() + "!");
        lblBoasVindas.setFont(new Font("Segoe UI", Font.BOLD, 16));
        painelSuperior.add(lblBoasVindas);

        JButton btnBuscarMedicos = new JButton("Buscar Médicos e Agendar");
        painelSuperior.add(btnBuscarMedicos);

        JButton btnCancelar = new JButton("Cancelar Agendamento");
        painelSuperior.add(btnCancelar);

        JButton btnAvaliar = new JButton("Avaliar Consulta");
        painelSuperior.add(btnAvaliar);

        add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"ID", "Data", "Médico", "Especialidade", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaConsultas = new JTable(tableModel);
        add(new JScrollPane(tabelaConsultas), BorderLayout.CENTER);

        btnBuscarMedicos.addActionListener(e -> abrirTelaDeBusca());
        btnCancelar.addActionListener(e -> cancelarConsultaSelecionada());
        btnAvaliar.addActionListener(e -> abrirTelaDeAvaliacao());
    }

    private void atualizarTabelaConsultas() {
        tableModel.setRowCount(0);

        
        List<Consulta> minhasConsultas = agendamentoService.getConsultasPorPaciente(pacienteLogado);

        for (Consulta consulta : minhasConsultas) {
            Object[] rowData = {
                    consulta.getId(),
                    consulta.getData().toString(),
                    consulta.getMedico().getNome(),
                    consulta.getMedico().getEspecialidade(),
                    consulta.getStatus().toString()
            };
            tableModel.addRow(rowData);
        }
    }

    private void abrirTelaDeBusca() {
        TelaBuscaMedicos telaBusca = new TelaBuscaMedicos(this, pacienteLogado, agendamentoService);
        telaBusca.setVisible(true);
        atualizarTabelaConsultas();
    }

    private void cancelarConsultaSelecionada() {
        int selectedRow = tabelaConsultas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para cancelar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int consultaId = (int) tableModel.getValueAt(selectedRow, 0);
        
        Consulta consultaSelecionada = agendamentoService.getConsultaPorId(consultaId);

        if (consultaSelecionada != null && consultaSelecionada.getStatus() == StatusConsulta.AGENDADA) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja cancelar a consulta com Dr(a). " + consultaSelecionada.getMedico().getNome() + "?",
                    "Confirmar Cancelamento",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                agendamentoService.cancelarAgendamento(consultaSelecionada);
                JOptionPane.showMessageDialog(this, "Consulta cancelada com sucesso!");
                atualizarTabelaConsultas();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Apenas consultas agendadas podem ser canceladas.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void abrirTelaDeAvaliacao() {
        int selectedRow = tabelaConsultas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para avaliar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int consultaId = (int) tableModel.getValueAt(selectedRow, 0);
        
        Consulta consulta = agendamentoService.getConsultaPorId(consultaId);

        if (consulta != null) {
            if (consulta.getStatus() == StatusConsulta.REALIZADA && consulta.getAvaliacao() == null) {
                TelaAvaliacao telaAvaliacao = new TelaAvaliacao(this, consulta);
                telaAvaliacao.setVisible(true);
                atualizarTabelaConsultas();
            } else {
                JOptionPane.showMessageDialog(this, "Esta consulta não pode ser avaliada.\n(Verifique se ela já foi realizada ou se já foi avaliada anteriormente).", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}