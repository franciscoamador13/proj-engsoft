package restauracao;

import javax.swing.*;
import java.util.List;
import java.awt.*;

public class criarbundle extends JFrame {
    private JPanel criarBundlePage;
    private JList<String> list1;
    private JTextField textField1; // preço
    private JTextField textField2; // tipo
    private JButton criarNovoBundleButton;
    private DadosRestauracao dadosRestauracao;
    private barMainPage mainPage;
    private DefaultListModel<String> listModel;

    public criarbundle(String title, barMainPage mainPage) {
        super(title);
        this.mainPage = mainPage;
        this.dadosRestauracao = DadosRestauracao.getInstance();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(criarBundlePage);
        
        // Configurar a lista de produtos
        listModel = new DefaultListModel<>();
        for (Produto produto : dadosRestauracao.getProdutos()) {
            listModel.addElement(produto.getNome());
        }
        list1.setModel(listModel);
        list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // Melhorar a aparência da lista
        list1.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));
        
        // Adicionar bordas aos campos
        textField1.setBorder(BorderFactory.createTitledBorder("Preço"));
        textField2.setBorder(BorderFactory.createTitledBorder("Tipo"));
        
        // Adicionar listener ao botão
        criarNovoBundleButton.addActionListener(e -> criarBundle());
        
        // Ajustar o tamanho mínimo da janela
        setMinimumSize(new Dimension(400, 500));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void criarBundle() {
        try {
            // Obter produtos selecionados
            List<String> produtosSelecionados = list1.getSelectedValuesList();
            if (produtosSelecionados.isEmpty()) {
                showError("Selecione pelo menos um produto para o bundle.", "Seleção vazia");
                return;
            }

            // Obter e validar preço
            String precoText = textField1.getText().trim().replace(",", ".");
            if (precoText.isEmpty()) {
                showError("Insira um preço para o bundle.", "Campo obrigatório");
                return;
            }
            double preco = Double.parseDouble(precoText);
            if (preco <= 0) {
                showError("O preço deve ser maior que zero.", "Valor inválido");
                return;
            }

            // Obter e validar tipo
            String tipo = textField2.getText().trim();
            if (tipo.isEmpty()) {
                showError("Insira um tipo para o bundle.", "Campo obrigatório");
                return;
            }

            // Criar e adicionar o novo bundle
            Bundle novoBundle = new Bundle(produtosSelecionados, preco, tipo);
            dadosRestauracao.adicionarBundle(novoBundle);

            // Atualizar a tabela na página principal
            mainPage.adicionarBundle(novoBundle.getProdutosString(), preco, novoBundle.getStockAgrupado(dadosRestauracao), tipo);

            // Mostrar mensagem de sucesso
            JOptionPane.showMessageDialog(this,
                    "Bundle criado com sucesso!\n" +
                            "Produtos: " + novoBundle.getProdutosString() + "\n" +
                            "Total de bundles: " + dadosRestauracao.getNumeroBundles(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Fechar a janela
            dispose();

        } catch (NumberFormatException ex) {
            showError("Por favor, insira um valor numérico válido para o preço (use vírgula para decimais).", "Erro de formato");
        }
    }

    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
