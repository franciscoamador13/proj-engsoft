package faturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class consultarFatura extends JFrame {
    private JPanel consultarFaturasPage;
    private JTable table1;
    private JButton verFaturaButton;
    private DefaultTableModel tableModel;
    private DadosFaturas dadosFaturas;

    public consultarFatura() {
        super("Consultar Fatura");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(consultarFaturasPage);

        dadosFaturas = DadosFaturas.getInstance();

        // Definir as colunas
        String[] colunas = {"Nº Fatura", "Data", "Total", "Nº Cliente", "NIF"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        table1.setModel(tableModel);

        // Configurar seleção da tabela
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Carregar faturas do sistema
        carregarFaturas();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Botão para ver fatura selecionada
        verFaturaButton.addActionListener(e -> verFaturaSelecionada());

        // Double click na tabela para ver fatura
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verFaturaSelecionada();
                }
            }
        });
    }

    private void carregarFaturas() {
        // Limpar tabela
        tableModel.setRowCount(0);

        // Carregar todas as faturas
        for (Fatura fatura : dadosFaturas.getFaturas()) {
            Object[] linha = {
                    fatura.getNumeroFatura(),
                    fatura.getData(),
                    String.format("%.2f€", fatura.getTotal()),
                    fatura.getNumeroCliente(),
                    fatura.getNif() != null && !fatura.getNif().isEmpty() ? fatura.getNif() : "N/A"
            };
            tableModel.addRow(linha);
        }
    }

    private void verFaturaSelecionada() {
        int linhaSelecionada = table1.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione uma fatura para visualizar.",
                    "Nenhuma fatura selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String numeroFatura = (String) tableModel.getValueAt(linhaSelecionada, 0);
        Fatura fatura = dadosFaturas.getFaturaPorNumero(numeroFatura);

        if (fatura != null) {
            new verFatura(fatura);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar a fatura selecionada.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}