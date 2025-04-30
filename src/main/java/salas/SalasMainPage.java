package salas;

import javax.swing.*;

public class SalasMainPage extends JFrame {
    private JPanel salasMainPage;
    private JLabel title;

    public SalasMainPage() {
        super("Página de Salas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(salasMainPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
