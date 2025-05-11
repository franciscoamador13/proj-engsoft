package restauracao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class barMainPage extends JFrame {
    private JPanel mainPageBar;
    private JTable table1;
    private JButton adicionarNovoProdutoButton;
    private JButton registarNovoStockButton;
    private JButton editarProdutoButton;
    private DefaultTableModel tableModel;

    public barMainPage() {
        super("Página de gestão de bar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(mainPageBar);


        String[] colunas = {"nome", "preco", "stock", "tipo"};
        tableModel = new DefaultTableModel(colunas, 0);
        table1.setModel(tableModel);


        adicionarProduto("Café", 1.20, 50, "Bebida");
        adicionarProduto("Cerveja", 2.50, 100, "Bebida Alcoólica");
        adicionarProduto("Refrigerante", 1.80, 75, "Bebida");
        adicionarProduto("Água Mineral", 1.00, 120, "Bebida");
        adicionarProduto("Sandes Mista", 3.50, 25, "Comida");
        adicionarProduto("Pastel de Nata", 1.30, 40, "Pastelaria");

        // Configurar larguras de colunas
        table1.getColumnModel().getColumn(0).setPreferredWidth(150);
        table1.getColumnModel().getColumn(1).setPreferredWidth(80);
        table1.getColumnModel().getColumn(2).setPreferredWidth(80);
        table1.getColumnModel().getColumn(3).setPreferredWidth(100);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarNovoProdutoButton.addActionListener(e -> {
            new adicionarProduto("Adiconar produto");
        });

        registarNovoStockButton.addActionListener(e -> {
            new adicionarStock("Adiconar stock");
        });

        editarProdutoButton.addActionListener(e -> {
            new editarProduto("Editar produto");
        });


    }


    private void adicionarProduto(String nome, double preco, int stock, String tipo) {
        Object[] linha = {nome, preco, stock, tipo};
        tableModel.addRow(linha);
    }

}