package restauracao;

import javax.swing.*;

public class removerStock extends JFrame {

    private JPanel removerStockPage;
    private JButton removerStockButton;
    private JComboBox comboBox1;
    private JTextField textField1;

    public removerStock() {
        super("Remover stock");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(removerStockPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
