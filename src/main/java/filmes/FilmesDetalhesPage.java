package filmes;

import javax.swing.*;

public class FilmesDetalhesPage extends JFrame{
    private JPanel filmesDetalhesPage;
    private JButton editarButton;

    public FilmesDetalhesPage(JLabel title) {
        super("Detalhes do Filme " + title.getText());
        String titleMovie = title.getText();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(filmesDetalhesPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        editarButton.addActionListener(e -> {
            new FilmesEditarDetalhesPage();
        });
    }
}
