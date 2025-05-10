import salas.SalasMainPage;
import sessoes.SessoesMainPage;

import javax.swing.*;

public class MenuPage extends JFrame {
    private JPanel menuPage;
    private JButton salasButton;
    private JButton sessoesButton;
    private JButton vendasButton;
    private JButton barButton;
    private JButton statsButton;
    private JButton catálogoButton;

    public MenuPage() {
        super("Menu Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(menuPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        salasButton.addActionListener(e -> new SalasMainPage());
        sessoesButton.addActionListener(e -> new SessoesMainPage());
        //vendasButton.addActionListener(e -> new VendasMainPage());
        //barButton.addActionListener(e -> new BarMainPage());
        //statsButton.addActionListener(e -> new StatsMainPage());
    }
}
