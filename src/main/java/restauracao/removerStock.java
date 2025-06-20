package restauracao;

import javax.swing.*;

public class removerStock extends JFrame {

    private JPanel removerStockPage;
    private JButton removerStockButton;
    private JComboBox<String> comboBox1;
    private JTextField textField1;
    private DadosRestauracao dadosRestauracao; // Referência ao singleton
    private barMainPage mainPage;

    public removerStock(barMainPage mainPage) {
        super("Remover stock");
        this.mainPage = mainPage;
        this.dadosRestauracao = DadosRestauracao.getInstance(); // Obter instância do singleton
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(removerStockPage);
        
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
