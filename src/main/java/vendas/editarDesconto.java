package vendas;

import javax.swing.*;

public class editarDesconto extends JFrame {

    private JPanel editarDescontoPage;
    private JButton confirmarButton;
    private JTextField textField1;
    private JTextField textField2;

    public editarDesconto() {
        super("Página de editar descontos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarDescontoPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
