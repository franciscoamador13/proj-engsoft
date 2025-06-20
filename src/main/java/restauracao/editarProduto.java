package restauracao;

import javax.swing.*;

public class editarProduto extends JFrame {
    private JComboBox<String> comboBox1;
    private JTextField cocaCola250mlTextField;
    private JTextField a2TextField;
    private JTextField bebidaTextField;
    private JPanel editarProdutoPage;
    private JButton submeterButton;
    private JButton removerProdutoButton;
    private DadosRestauracao dadosRestauracao; // Referência ao singleton
    private barMainPage mainPage;

    public editarProduto(String title, barMainPage mainPage) {
        super(title);
        this.mainPage = mainPage;
        this.dadosRestauracao = DadosRestauracao.getInstance(); // Obter instância do singleton
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarProdutoPage);
        
        // Populate comboBox with product names
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Produto produto : dadosRestauracao.getProdutos()) {
            model.addElement(produto.getNome());
        }
        comboBox1.setModel(model);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
