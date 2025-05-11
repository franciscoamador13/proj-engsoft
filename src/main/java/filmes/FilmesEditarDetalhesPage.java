package filmes;

import javax.swing.*;

public class FilmesEditarDetalhesPage extends JFrame {
    private JPanel editarDetalhesFilme;
    private JButton confirmarButton;
    private JButton limparButton;

    public FilmesEditarDetalhesPage() {
        super("Editar Detalhes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarDetalhesFilme);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        confirmarButton.addActionListener(e -> {
            setVisible(false);
        });
    }
}