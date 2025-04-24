import salas.SalasMainPage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuPage extends JFrame {

    private JPanel menuPage;
    private JButton salasButton;
    private JButton sessoesButton;
    private JButton vendasButton;
    private JButton barButton;
    private JButton statsButton;

    public MenuPage() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(menuPage);
        pack();

        salasButton.addActionListener(this::salasButtonActionPerformed);
    }

    private void salasButtonActionPerformed(ActionEvent e) {
        System.out.println("Salas button clicked!"); // Debugging
        setContentPane(new SalasMainPage().getContentPane());
        revalidate();
        repaint();
    }
}