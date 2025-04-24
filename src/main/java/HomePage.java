import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import salas.*;

public class HomePage extends JFrame {
    private JPanel homePage;
    private JButton startButton;

    public HomePage(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(homePage);
        pack();

        startButton.addActionListener(this::startButtonActionPerformed);
    }

    private void startButtonActionPerformed(ActionEvent e) {
        setContentPane(new MenuPage().getContentPane());
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new HomePage("Cinema Management App").setVisible(true);
    }
}
