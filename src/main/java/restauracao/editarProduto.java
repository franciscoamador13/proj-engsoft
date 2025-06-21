package restauracao;

import javax.swing.*;
import java.awt.*;

public class editarProduto extends JFrame {
    private JComboBox<String> produtoComboBox;
    private JTextField nomeTextField;
    private JTextField precoTextField;
    private JTextField tipoTextField;
    private JPanel editarProdutoPage;
    private JButton submeterButton;
    private JButton removerProdutoButton;
    private DadosRestauracao dadosRestauracao;
    private barMainPage mainPage;

    public editarProduto(String title, barMainPage mainPage) {
        super(title);
        this.mainPage = mainPage;
        this.dadosRestauracao = DadosRestauracao.getInstance();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarProdutoPage);

        // Configurar o ComboBox
        atualizarComboBox();

        // Adicionar listener para atualizar campos quando um produto é selecionado
        produtoComboBox.addActionListener(e -> atualizarCampos());

        // Configurar botão de submeter
        submeterButton.addActionListener(e -> editarProduto());

        // Configurar botão de remover
        removerProdutoButton.addActionListener(e -> removerProduto());

        // Estilizar o botão de remover
        removerProdutoButton.setBackground(new Color(220, 53, 69));
        removerProdutoButton.setForeground(Color.WHITE);
        removerProdutoButton.setFocusPainted(false);

        // Inicializar campos se houver produtos
        if (produtoComboBox.getItemCount() > 0) {
            atualizarCampos();
        }

        // Configurar tamanho e posição
        setMinimumSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarComboBox() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Produto produto : dadosRestauracao.getProdutos()) {
            model.addElement(produto.getNome());
        }
        produtoComboBox.setModel(model);
    }

    private void atualizarCampos() {
        String nomeProduto = (String) produtoComboBox.getSelectedItem();
        if (nomeProduto != null) {
            Produto produto = dadosRestauracao.getProdutoPorNome(nomeProduto);
            if (produto != null) {
                nomeTextField.setText(produto.getNome());
                precoTextField.setText(String.format("%.2f", produto.getPreco()).replace(".", ","));
                tipoTextField.setText(produto.getTipo());
            }
        }
    }

    private void editarProduto() {
        try {
            String nomeProdutoOriginal = (String) produtoComboBox.getSelectedItem();
            if (nomeProdutoOriginal == null) {
                mostrarErro("Selecione um produto para editar.");
                return;
            }

            // Validar campos
            String novoNome = nomeTextField.getText().trim();
            if (novoNome.isEmpty()) {
                mostrarErro("O nome do produto não pode estar vazio.");
                return;
            }

            // Validar e converter preço
            String precoTexto = precoTextField.getText().trim().replace(",", ".");
            if (precoTexto.isEmpty()) {
                mostrarErro("O preço não pode estar vazio.");
                return;
            }

            double novoPreco;
            try {
                novoPreco = Double.parseDouble(precoTexto);
                if (novoPreco <= 0) {
                    mostrarErro("O preço deve ser maior que zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarErro("Preço inválido. Use vírgula para decimais.");
                return;
            }

            String novoTipo = tipoTextField.getText().trim();
            if (novoTipo.isEmpty()) {
                mostrarErro("O tipo não pode estar vazio.");
                return;
            }

            // Atualizar o produto
            Produto produtoAtual = dadosRestauracao.getProdutoPorNome(nomeProdutoOriginal);
            if (produtoAtual != null) {
                // Verificar se o novo nome já existe (exceto se for o mesmo produto)
                if (!novoNome.equals(nomeProdutoOriginal) && dadosRestauracao.getProdutoPorNome(novoNome) != null) {
                    mostrarErro("Já existe um produto com este nome.");
                    return;
                }

                produtoAtual.setNome(novoNome);
                produtoAtual.setPreco(novoPreco);
                produtoAtual.setTipo(novoTipo);

                // Salvar alterações
                dadosRestauracao.gravarDados();

                // Atualizar interface
                mainPage.atualizarTabela();
                atualizarComboBox();

                // Mostrar mensagem de sucesso
                JOptionPane.showMessageDialog(this,
                        "Produto atualizado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
        } catch (Exception e) {
            mostrarErro("Erro ao editar produto: " + e.getMessage());
        }
    }

    private void removerProduto() {
        String nomeProduto = (String) produtoComboBox.getSelectedItem();
        if (nomeProduto == null) {
            mostrarErro("Selecione um produto para remover.");
            return;
        }

        // Confirmar remoção
        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover o produto '" + nomeProduto + "'?\n" +
                        "Esta ação não pode ser desfeita.",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                // Remover o produto
                dadosRestauracao.removerProduto(nomeProduto);

                // Salvar alterações
                dadosRestauracao.gravarDados();

                // Atualizar interface
                mainPage.atualizarTabela();

                // Mostrar mensagem de sucesso
                JOptionPane.showMessageDialog(this,
                        "Produto removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                dispose();
            } catch (Exception e) {
                mostrarErro("Erro ao remover produto: " + e.getMessage());
            }
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

}
