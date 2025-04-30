package salas;

import javax.swing.*;

public class AdicionarNovasSalas extends JFrame {
    private JButton confirmarButton;
    private JPanel adicionarSala;

    public AdicionarNovasSalas() {
        super("Menu Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarSala);
        pack();
        setVisible(true);

    }
}
