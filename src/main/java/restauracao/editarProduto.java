package restauracao;

import javax.swing.*;

public class editarProduto extends JFrame {
    private JComboBox comboBox1;
    private JTextField cocaCola250mlTextField;
    private JTextField a2TextField;
    private JTextField bebidaTextField;
    private JPanel editarProdutoPage;
    private JButton submeterButton;
    private JButton removerProdutoButton;


    public editarProduto(String title) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarProdutoPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
