package restauracao;

import javax.swing.*;

public class criarbundle extends JFrame {
    private JPanel criarBundlePage;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton criarNovoBundleButton;

    public criarbundle(String title) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(criarBundlePage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
