package restauracao;

import javax.swing.*;

public class adicionarStock extends JFrame {

    private JPanel adicionarStock;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton adicionarButton;

    public adicionarStock(String title) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarStock);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
