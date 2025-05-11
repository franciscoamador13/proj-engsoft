package filmes;

import javax.swing.*;

public class FilmesMainPage extends JFrame {
    private JPanel filmesMain;
    private JButton sessõesButton;
    private JList list1;
    private JButton detalhesButton1;
    private JButton detalhesButton2;
    private JButton detalhesButton3;
    private JButton detalhesButton4;
    private JLabel title_filme1;
    private JLabel title_filme2;
    private JLabel title_filme3;
    private JLabel title_filme4;
    private JButton adicionarFilmesButton;

    public FilmesMainPage() {
        super("Página de Filmes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(filmesMain);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarFilmesButton.addActionListener(e -> {
            new FilmesAdicionarPage();
        });

        detalhesButton1.addActionListener(e -> {
            new FilmesDetalhesPage(title_filme1);
        });
        detalhesButton2.addActionListener(e -> {
            new FilmesDetalhesPage(title_filme2);
        });
        detalhesButton3.addActionListener(e -> {
            new FilmesDetalhesPage(title_filme3);
        });
        detalhesButton4.addActionListener(e -> {
            new FilmesDetalhesPage(title_filme4);
        });
    }
}
