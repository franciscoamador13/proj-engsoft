package vendas;

import javax.swing.*;

public class adicionarDesconto extends JFrame {

    private JPanel adicionarDescontoPage;
    private JButton adicionarButton;
    private JTextField textField1;
    private JTextField textField2;

    public adicionarDesconto() {
        super("Adicionar desconto");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarDescontoPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
