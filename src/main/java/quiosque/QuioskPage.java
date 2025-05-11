package quiosque;

import javax.swing.*;

public class QuioskPage extends JFrame {


    private JPanel quioskPage;
    private JList list2;
    private JRadioButton bilheteNormalRadioButton;
    private JRadioButton bilheteComDescontoRadioButton;
    private JComboBox comboBox1;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;

    public QuioskPage() {
        super("Página quiosque");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(quioskPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
