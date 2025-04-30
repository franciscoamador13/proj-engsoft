import salas.SalasMainPage;

import javax.swing.*;

public class MenuPage extends JFrame {
    private JPanel menuPage;
    private JButton salasButton;
    private JButton sessoesButton;
    private JButton vendasButton;
    private JButton barButton;
    private JButton statsButton;

    public MenuPage() {
        super("Menu Principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(menuPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        salasButton.addActionListener(e -> new SalasMainPage());
    }
}
