package restauracao;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class adicionarProduto extends JFrame {

    private JPanel adicionarProdutoPage;
    private JTextField nomeProdutoField;
    private JTextField precoProdutoField;
    private JTextField stockAtualField;
    private JButton adicionarButton;
    private JTextField tipoProdutoField;
    private DadosRestauracao dadosRestauracao; // Referência ao singleton
    private barMainPage mainPage;

    public adicionarProduto(String title, barMainPage mainPage) {
        super(title);
        this.mainPage = mainPage;
        this.dadosRestauracao = DadosRestauracao.getInstance(); // Obter instância do singleton

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarProdutoPage);

        // Adicionar listener ao botão
        adicionarButton.addActionListener(e -> adicionarProduto());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarProduto() {
        try {
            // Obter valores dos campos
            String nome = nomeProdutoField.getText().trim();
            String precoText = precoProdutoField.getText().trim();
            String stockText = stockAtualField.getText().trim();
            String tipo = tipoProdutoField.getText().trim();

            // Validar campos obrigatórios
            if (nome.isEmpty() || precoText.isEmpty() || stockText.isEmpty() || tipo.isEmpty()) {
                showError("Todos os campos são obrigatórios.", "Campos em falta");
                return;
            }

            // Verificar se já existe um produto com este nome
            if (dadosRestauracao.existeProduto(nome)) {
                showError("Já existe um produto com o nome '" + nome + "'.", "Nome duplicado");
                return;
            }

            // Converter e validar valores numéricos
            double preco = Double.parseDouble(precoText);
            int stock = Integer.parseInt(stockText);

            if (preco <= 0) {
                showError("O preço deve ser maior que zero.", "Valor inválido");
                return;
            }

            if (stock < 0) {
                showError("O stock não pode ser negativo.", "Valor inválido");
                return;
            }

            // Criar e adicionar o novo produto
            Produto novoProduto = new Produto(nome, preco, stock, tipo);
            dadosRestauracao.adicionarProduto(novoProduto);

            // Atualizar a tabela na página principal
            mainPage.adicionarProduto(nome, preco, stock, tipo);

            // Mostrar mensagem de sucesso
            JOptionPane.showMessageDialog(this,
                    "Produto '" + nome + "' adicionado com sucesso!\n" +
                            "Total de produtos: " + dadosRestauracao.getNumeroProdutos(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Fechar a janela
            dispose();

        } catch (NumberFormatException ex) {
            showError("Por favor, insira valores numéricos válidos para preço e stock.", "Erro de formato");
        }
    }

    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

}
