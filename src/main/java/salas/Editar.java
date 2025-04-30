package salas;

import javax.swing.*;

public class Editar extends JFrame {
    private JPanel editarPage;
    private JButton confrimarAlteraçõesButton;

    public Editar() {
        super("Menu Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarPage);
        pack();
        setVisible(true);

    }
}
