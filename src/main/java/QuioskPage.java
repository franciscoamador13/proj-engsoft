import salas.SalasMainPage;

import javax.swing.*;

public class QuioskPage extends JFrame {


    private JPanel quioskPage;

    public QuioskPage() {
        super("Página quiosk");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(quioskPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
