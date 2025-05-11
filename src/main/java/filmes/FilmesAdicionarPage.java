package filmes;

import javax.swing.*;

public class FilmesAdicionarPage extends JFrame {
    private JPanel filmesAdicionarPage;
    private JButton adicionarButton;

    public FilmesAdicionarPage() {
        super("Adicionar Filme");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(filmesAdicionarPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarButton.addActionListener(e -> {
            setVisible(false);
        });
    }
}
