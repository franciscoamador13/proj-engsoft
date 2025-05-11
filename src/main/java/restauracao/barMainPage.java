package restauracao;

import javax.swing.*;

public class barMainPage extends JFrame {
    private JPanel mainPageBar;

    public barMainPage() {
        super("Página de gestão de bar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(mainPageBar);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
