import quiosque.QuioskPage;

import javax.swing.*;

public class HomePage extends JFrame {
    private JPanel homePage;
    private JButton startButton;
    private JButton quioskButton;

    public HomePage(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(homePage);
        pack();

        startButton.addActionListener(e -> {
            new MenuPage();
        });

        quioskButton.addActionListener(e -> {
            QuioskPage.getInstance().setVisible(true);
        });
    }

    public static void main(String[] args) {
        new HomePage("Cinema Management App").setVisible(true);
    }
}
