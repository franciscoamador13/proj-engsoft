package filmes;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;

public class FilmesMainPage extends JFrame {
    private JPanel filmesMain;
    private JButton adicionarFilmesButton;
    private JButton sessoesButton;
    private JButton detalhesButton;
    private JButton eliminarButton;
    private JTable filmesTable;
    private JComboBox<String> filtroComboBox;
    private JButton filtrarButton;

    private DefaultTableModel tableModel;
    private DadosFilmes dadosFilmes;

    public FilmesMainPage() {
        dadosFilmes = DadosFilmes.getInstance();
        setContentPane(filmesMain);
        setTitle("Gestão de Filmes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupTable();
        atualizarTabela("Todos");

        adicionarFilmesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmesAdicionarPage filmesAdicionarPage = new FilmesAdicionarPage();
                filmesAdicionarPage.setVisible(true);
                dispose();
            }
        });

        detalhesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = filmesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String titulo = (String) filmesTable.getValueAt(selectedRow, 4);
                    FilmesDetalhesPage filmesDetalhesPage = new FilmesDetalhesPage(titulo);
                    filmesDetalhesPage.setVisible(true);
                    dispose();
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = filmesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String titulo = (String) filmesTable.getValueAt(selectedRow, 4);
                    int option = JOptionPane.showConfirmDialog(null,
                            "Tem a certeza que pretende eliminar o filme " + titulo + "?",
                            "Confirmar Eliminação",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        dadosFilmes.removerFilme(titulo);
                        atualizarTabela((String) filtroComboBox.getSelectedItem());
                    }
                }
            }
        });

        filmesTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = filmesTable.getSelectedRow() != -1;
            detalhesButton.setEnabled(rowSelected);
            eliminarButton.setEnabled(rowSelected);
            sessoesButton.setEnabled(rowSelected);
        });

        filtrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtroSelecionado = (String) filtroComboBox.getSelectedItem();
                atualizarTabela(filtroSelecionado);
            }
        });

        // Add double-click listener to open details
        filmesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = filmesTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String titulo = (String) filmesTable.getValueAt(selectedRow, 4);
                        FilmesDetalhesPage filmesDetalhesPage = new FilmesDetalhesPage(titulo);
                        filmesDetalhesPage.setVisible(true);
                        dispose();
                    }
                }
            }
        });

        pack();
        setVisible(true);
    }

    private void setupTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Tipo");
        tableModel.addColumn("Realização");
        tableModel.addColumn("Duração");
        tableModel.addColumn("Data de Lançamento");
        tableModel.addColumn("Título");
        tableModel.addColumn("Estado");

        filmesTable.setModel(tableModel);
        filmesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void atualizarTabela(String filtro) {
        tableModel.setRowCount(0);
        List<Filme> filmes = dadosFilmes.getFilmesFiltrados(filtro);

        for (Filme filme : filmes) {
            tableModel.addRow(new Object[]{
                    filme.getTipo(),
                    filme.getRealizacao(),
                    filme.getDuracao(),
                    filme.getDataLancamento(),
                    filme.getTitulo(),
                    filme.isAtivo() ? "Ativo" : "Inativo"
            });
        }
    }

}
