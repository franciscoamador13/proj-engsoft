package restauracao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class barMainPage extends JFrame {
    private JPanel mainPageBar;
    private JTable table1;
    private JButton adicionarNovoProdutoButton;
    private JButton registarNovoStockButton;
    private JButton editarProdutoButton;
    private JButton criarBundleButton;
    private JTable table2;
    private DefaultTableModel tableModelProdutos;
    private DefaultTableModel tableModelBundles;

    public barMainPage() {
        super("Página de gestão de bar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(mainPageBar);

        // Configurar tabela de Produtos
        String[] colunasProdutos = {"nome", "preco", "stock", "tipo"};
        tableModelProdutos = new DefaultTableModel(colunasProdutos, 0);
        table1.setModel(tableModelProdutos);

        // Configurar tabela de Bundles
        String[] colunasBundles = {"Produtos no bundle", "preco", "stock agrupado", "tipo"};
        tableModelBundles = new DefaultTableModel(colunasBundles, 0);
        table2.setModel(tableModelBundles);

        // Adicionar produtos
        adicionarProduto("Café", 1.20, 50, "Bebida");
        adicionarProduto("Cerveja", 2.50, 100, "Bebida Alcoólica");
        adicionarProduto("Refrigerante", 1.80, 75, "Bebida");
        adicionarProduto("Água Mineral", 1.00, 120, "Bebida");
        adicionarProduto("Sandes Mista", 3.50, 25, "Comida");
        adicionarProduto("Pastel de Nata", 1.30, 40, "Pastelaria");
        adicionarProduto("Pipoca", 2.00, 60, "Snack");

        // Adicionar bundles de exemplo
        adicionarBundle("Pipoca + Refrigerante", 3.50, 50, "Combo Cinema");
        adicionarBundle("Sandes Mista + Água Mineral", 4.20, 30, "Combo Lanche");
        adicionarBundle("Café + Pastel de Nata", 2.30, 40, "Combo Café");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarNovoProdutoButton.addActionListener(e -> {
            new adicionarProduto("Adicionar produto");
        });

        registarNovoStockButton.addActionListener(e -> {
            new adicionarStock("Adicionar stock");
        });

        editarProdutoButton.addActionListener(e -> {
            new editarProduto("Editar produto");
        });

        criarBundleButton.addActionListener(e -> {
            new criarbundle("Criar bundle");
        });

    }

    private void adicionarProduto(String nome, double preco, int stock, String tipo) {
        Object[] linha = {nome, preco, stock, tipo};
        tableModelProdutos.addRow(linha);
    }


    private void adicionarBundle(String produtos, double preco, int stock, String tipo) {
        Object[] linha = {produtos, preco, stock, tipo};
        tableModelBundles.addRow(linha);
    }

}