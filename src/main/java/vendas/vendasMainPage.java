package vendas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class vendasMainPage extends JFrame {
    private static vendasMainPage instance = null;
    private JPanel vendasMainPage;
    private JButton adicionarDescontoButton;
    private JButton alterarPrecoButton;
    private JButton editarDescontoButton;
    private JTable descontosTable;
    private JLabel precoBilheteLabel;
    private DefaultTableModel tableModel;

    private vendasMainPage() {
        super("Gestão de Vendas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(vendasMainPage);

        // Configurar a tabela
        String[] colunas = {"Condição", "Valor (%)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        descontosTable.setModel(tableModel);

        // Configurar eventos dos botões
        adicionarDescontoButton.addActionListener(e -> onAdicionarDesconto());
        alterarPrecoButton.addActionListener(e -> onAlterarPreco());
        editarDescontoButton.addActionListener(e -> onEditarDesconto());

        // Atualizar dados ao iniciar
        atualizarDados();

        pack();
        setLocationRelativeTo(null);
    }

    public static vendasMainPage getInstance() {
        if (instance == null) {
            instance = new vendasMainPage();
        }
        return instance;
    }

    private void atualizarDados() {
        DadosVendas dadosVendas = DadosVendas.getInstance();
        
        // Atualizar preço do bilhete
        precoBilheteLabel.setText(String.format("Preço atual do bilhete: %.2f€", dadosVendas.getPrecoBilhete()));

        // Limpar e atualizar tabela de descontos
        tableModel.setRowCount(0);
        List<Desconto> descontos = dadosVendas.getDescontos();
        for (Desconto desconto : descontos) {
            tableModel.addRow(new Object[]{
                desconto.getCondicao(),
                String.format("%.2f", desconto.getValor())
            });
        }
    }

    private void onAdicionarDesconto() {
        adicionarDesconto dialog = adicionarDesconto.getInstance();
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        atualizarDados();
    }

    private void onAlterarPreco() {
        alterarPrecoBilhete dialog = alterarPrecoBilhete.getInstance();
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        atualizarDados();
    }

    private void onEditarDesconto() {
        int selectedRow = descontosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um desconto para editar.",
                    "Nenhum desconto selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String condicao = (String) descontosTable.getValueAt(selectedRow, 0);
        DadosVendas dadosVendas = DadosVendas.getInstance();
        Desconto desconto = dadosVendas.getDescontoPorCondicao(condicao);

        if (desconto != null) {
            editarDesconto dialog = editarDesconto.getInstance();
            dialog.setDesconto(desconto);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            atualizarDados();
        }
    }

    public void refreshData() {
        atualizarDados();
    }
}