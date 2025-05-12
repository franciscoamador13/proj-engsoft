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
    private JButton removerStockButton;
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
        adicionarProduto("Pipoca pequena", 2.50, "Aperitivo");
        adicionarProduto("Coca-cola 250ml", 1.80, 75, "Bebida");
        adicionarProduto("Água 33cl", 1.00, 120, "Bebida");

        // Adicionar bundles de exemplo
        adicionarBundle("Pipoca pequena + Coca-cola 250ml", 5.50, 50, "Combo Cinema");

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

        removerStockButton.addActionListener(e -> {
            new removerStock();
        });

    }

    // Método sobrecarregado que aceita 3 parâmetros (sem stock)
    private void adicionarProduto(String nome, double preco, String tipo) {
        // Chama o método de 4 parâmetros com um valor padrão para o stock (0)
        adicionarProduto(nome, preco, 0, tipo);
    }

    // Método original que aceita 4 parâmetros (com stock)
    private void adicionarProduto(String nome, double preco, int stock, String tipo) {
        Object[] linha = {nome, preco, stock, tipo};
        tableModelProdutos.addRow(linha);
    }

    // Método sobrecarregado que aceita 3 parâmetros (sem stock)
    private void adicionarBundle(String produtos, double preco, String tipo) {
        // Chama o método de 4 parâmetros com um valor padrão para o stock (0)
        adicionarBundle(produtos, preco, 0, tipo);
    }

    // Método original que aceita 4 parâmetros (com stock)
    private void adicionarBundle(String produtos, double preco, int stock, String tipo) {
        Object[] linha = {produtos, preco, stock, tipo};
        tableModelBundles.addRow(linha);
    }
}