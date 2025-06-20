package sessoes;

import filmes.DadosFilmes;
import filmes.Filme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class SessoesMainPage extends JFrame {
    private JPanel sessoesMainPage;
    private JComboBox<String> filmeComboBox;
    private JButton filtrarButton;
    private JButton limparButton;
    private JTable sessoesTable;
    private JButton adicionarNovaSessaoButton;
    private JButton detalhesButton;
    private JButton eliminarButton;

    private DefaultTableModel tableModel;
    private DadosSessoes dadosSessoes;
    private DadosFilmes dadosFilmes;

    public SessoesMainPage() {
        dadosSessoes = DadosSessoes.getInstance();
        dadosFilmes = DadosFilmes.getInstance();
        
        setContentPane(sessoesMainPage);
        setTitle("Gestão de Sessões");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupTable();
        carregarFilmes();
        atualizarTabela("Todos");

        adicionarNovaSessaoButton.addActionListener(e -> {
            AdicionarSessaoPage adicionarSessaoPage = new AdicionarSessaoPage();
            adicionarSessaoPage.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    atualizarTabela((String) filmeComboBox.getSelectedItem());
                }
            });
            adicionarSessaoPage.setVisible(true);
        });

        detalhesButton.addActionListener(e -> {
            int selectedRow = sessoesTable.getSelectedRow();
            if (selectedRow != -1) {
                String titulo = (String) sessoesTable.getValueAt(selectedRow, 0);
                String data = (String) sessoesTable.getValueAt(selectedRow, 1);
                String hora = (String) sessoesTable.getValueAt(selectedRow, 2);
                String sala = (String) sessoesTable.getValueAt(selectedRow, 3);
                SessoesDetalhesPage sessoesDetalhesPage = new SessoesDetalhesPage(titulo, data, hora, sala);
                sessoesDetalhesPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        atualizarTabela((String) filmeComboBox.getSelectedItem());
                    }
                });
                sessoesDetalhesPage.setVisible(true);
            }
        });

        eliminarButton.addActionListener(e -> {
            int selectedRow = sessoesTable.getSelectedRow();
            if (selectedRow != -1) {
                String titulo = (String) sessoesTable.getValueAt(selectedRow, 0);
                String data = (String) sessoesTable.getValueAt(selectedRow, 1);
                String hora = (String) sessoesTable.getValueAt(selectedRow, 2);
                String sala = (String) sessoesTable.getValueAt(selectedRow, 3);
                
                int option = JOptionPane.showConfirmDialog(null,
                        "Tem a certeza que pretende eliminar a sessão " + titulo + " - " + data + " " + hora + "?",
                        "Confirmar Eliminação",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    dadosSessoes.removerSessao(titulo, data, hora, sala);
                    atualizarTabela((String) filmeComboBox.getSelectedItem());
                }
            }
        });

        sessoesTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = sessoesTable.getSelectedRow() != -1;
            detalhesButton.setEnabled(rowSelected);
            eliminarButton.setEnabled(rowSelected);
        });

        filtrarButton.addActionListener(e -> {
            String filtroSelecionado = (String) filmeComboBox.getSelectedItem();
            atualizarTabela(filtroSelecionado);
        });

        limparButton.addActionListener(e -> {
            filmeComboBox.setSelectedIndex(0);
            atualizarTabela("Todos");
        });

        // Add double-click listener to open details
        sessoesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = sessoesTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String titulo = (String) sessoesTable.getValueAt(selectedRow, 0);
                        String data = (String) sessoesTable.getValueAt(selectedRow, 1);
                        String hora = (String) sessoesTable.getValueAt(selectedRow, 2);
                        String sala = (String) sessoesTable.getValueAt(selectedRow, 3);
                        SessoesDetalhesPage sessoesDetalhesPage = new SessoesDetalhesPage(titulo, data, hora, sala);
                        sessoesDetalhesPage.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                atualizarTabela((String) filmeComboBox.getSelectedItem());
                            }
                        });
                        sessoesDetalhesPage.setVisible(true);
                    }
                }
            }
        });

        pack();
        setVisible(true);
    }

    private void carregarFilmes() {
        filmeComboBox.removeAllItems();
        filmeComboBox.addItem("Todos");
        
        List<Filme> filmes = dadosFilmes.getFilmes();
        for (Filme filme : filmes) {
            filmeComboBox.addItem(filme.getTitulo());
        }
    }

    private void setupTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Filme");
        tableModel.addColumn("Data");
        tableModel.addColumn("Hora");
        tableModel.addColumn("Sala");
        tableModel.addColumn("Estado");

        sessoesTable.setModel(tableModel);
        sessoesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void atualizarTabela(String filtro) {
        tableModel.setRowCount(0);
        List<Sessao> sessoes = dadosSessoes.getSessoesFiltradas(filtro, null);
        
        for (Sessao sessao : sessoes) {
            tableModel.addRow(new Object[]{
                sessao.getTitulo(),
                sessao.getData(),
                sessao.getHora(),
                sessao.getSala(),
                sessao.isAtiva() ? "Ativa" : "Inativa"
            });
        }
    }
}
