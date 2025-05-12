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
    private JButton escolherLugarButton;
    private JList list3;
    private JButton confirmarButton;

    public QuioskPage() {
        super("Página quiosque");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(quioskPage);


        DefaultComboBoxModel<String> descontoModel = new DefaultComboBoxModel<>();
        descontoModel.addElement("Desconto sénior +65 - 8€");
        descontoModel.addElement("Desconto estudante - 6€");
        descontoModel.addElement("Desconto família numerosa - 7€");
        comboBox1.setModel(descontoModel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
