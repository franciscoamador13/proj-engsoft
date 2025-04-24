package salas;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SalasMainPage extends JFrame {
    private JLabel title;
    private JPanel salasMainPage;

    public SalasMainPage() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(salasMainPage);
        pack();
    }

    public JPanel getContent() {
        return salasMainPage;
    }
}
