package restauracao;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class barMainPage extends JFrame {
    private JPanel mainPageBar;
    private JTable table1;
    private JButton adicionarNovoProdutoButton;
    private JButton registarNovoStockButton;
    private JButton editarProdutoButton;
    private JButton criarBundleButton;
    private JButton removerBundleButton;
    private JTable table2;
    private DefaultTableModel tableModelProdutos;
    private DefaultTableModel tableModelBundles;
    private DadosRestauracao dadosRestauracao;

    public barMainPage() {
        super("Página de gestão de bar");
        this.dadosRestauracao = DadosRestauracao.getInstance();
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

        // Carregar produtos existentes
        for (Produto produto : dadosRestauracao.getProdutos()) {
            adicionarProduto(
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getStock(),
                    produto.getTipo()
            );
        }

        // Carregar bundles existentes
        for (Bundle bundle : dadosRestauracao.getBundles()) {
            adicionarBundle(
                    bundle.getProdutosString(),
                    bundle.getPreco(),
                    bundle.getStockAgrupado(dadosRestauracao),
                    bundle.getTipo()
            );
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarNovoProdutoButton.addActionListener(e -> {
            new adicionarProduto("Adicionar produto", this);
        });

        registarNovoStockButton.addActionListener(e -> {
            new GerirStock(this);
        });

        editarProdutoButton.addActionListener(e -> {
            new editarProduto("Editar produto", this);
        });

        criarBundleButton.addActionListener(e -> {
            new criarbundle("Criar bundle", this);
        });

        removerBundleButton.addActionListener(e -> {
            int selectedRow = table2.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, selecione um bundle para remover.",
                        "Nenhum bundle selecionado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String bundleString = (String) table2.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o bundle:\n" + bundleString + "?",
                    "Confirmar remoção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                dadosRestauracao.removerBundle(bundleString);
                atualizarTabela();
                JOptionPane.showMessageDialog(this,
                        "Bundle removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // Método sobrecarregado que aceita 3 parâmetros (sem stock)
    public void adicionarProduto(String nome, double preco, String tipo) {
        // Chama o método de 4 parâmetros com um valor padrão para o stock (0)
        adicionarProduto(nome, preco, 0, tipo);
    }

    // Método original que aceita 4 parâmetros (com stock)
    public void adicionarProduto(String nome, double preco, int stock, String tipo) {
        Object[] linha = {nome, String.format("%.2f", preco).replace(".", ","), stock, tipo};
        tableModelProdutos.addRow(linha);
    }

    // Método sobrecarregado que aceita 3 parâmetros (sem stock)
    public void adicionarBundle(String produtos, double preco, String tipo) {
        // Chama o método de 4 parâmetros com um valor padrão para o stock (0)
        adicionarBundle(produtos, preco, 0, tipo);
    }

    // Método original que aceita 4 parâmetros (com stock)
    public void adicionarBundle(String produtos, double preco, int stock, String tipo) {
        Object[] linha = {produtos, String.format("%.2f", preco).replace(".", ","), stock, tipo};
        tableModelBundles.addRow(linha);
    }

    public void atualizarTabela() {
        // Limpar as tabelas
        tableModelProdutos.setRowCount(0);
        tableModelBundles.setRowCount(0);

        // Recarregar produtos
        for (Produto produto : dadosRestauracao.getProdutos()) {
            adicionarProduto(
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getStock(),
                    produto.getTipo()
            );
        }

        // Recarregar bundles
        for (Bundle bundle : dadosRestauracao.getBundles()) {
            adicionarBundle(
                    bundle.getProdutosString(),
                    bundle.getPreco(),
                    bundle.getStockAgrupado(dadosRestauracao),
                    bundle.getTipo()
            );
        }
    }

}