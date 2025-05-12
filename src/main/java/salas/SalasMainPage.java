package salas;

import javax.swing.*;

public class SalasMainPage extends JFrame {
    private JPanel salasMainPage;
    private JLabel title;
    private JButton adicionarNovasSalas;
    private JList list1;
    private JButton editarSalaButton;

    public SalasMainPage() {
        super("Página de Salas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(salasMainPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        editarSalaButton.addActionListener(e -> new editarSala());
        adicionarNovasSalas.addActionListener(e -> new AdicionarNovasSalas());


    }
}
