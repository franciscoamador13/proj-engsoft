package filmes;

import javax.swing.*;

public class FilmesAdicionarPage extends JFrame {
    private JPanel filmesAdicionarPage;
    private JButton adicionarButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

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
