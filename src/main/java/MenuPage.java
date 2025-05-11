import filmes.FilmesMainPage;
import salas.SalasMainPage;
import sessoes.SessoesMainPage;
import vendas.vendasMainPage;
import restauracao.barMainPage;

import javax.swing.*;

public class MenuPage extends JFrame {
    private JPanel menuPage;
    private JButton salasButton;
    private JButton sessoesButton;
    private JButton vendasButton;
    private JButton barButton;
    private JButton statsButton;
    private JButton filmesButton;

    public MenuPage() {
        super("Menu Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(menuPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        salasButton.addActionListener(e -> new SalasMainPage());
        sessoesButton.addActionListener(e -> new SessoesMainPage());
        vendasButton.addActionListener(e -> new vendasMainPage());
        barButton.addActionListener(e -> new barMainPage());
        filmesButton.addActionListener(e -> new FilmesMainPage());
        //statsButton.addActionListener(e -> new StatsMainPage());
    }
}
