package restauracao;

import javax.swing.*;

public class adicionarProduto extends JFrame {

    private JPanel adicionarProdutoPage;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton adicionarButton;
    private JTextField textField4;

    public adicionarProduto(String title) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarProdutoPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
