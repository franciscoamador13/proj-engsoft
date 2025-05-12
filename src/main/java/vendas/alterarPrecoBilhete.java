package vendas;

import javax.swing.*;

public class alterarPrecoBilhete extends JFrame {
    private JPanel alterarPrecoBilhetePage;
    private JButton confirmarButton;
    private JTextField textField1;

    public alterarPrecoBilhete() {
        super("Alterar preço");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(alterarPrecoBilhetePage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
