package salas;

import javax.swing.*;

public class SalasMainPage extends JFrame {
    private JPanel salasMainPage;
    private JLabel title;
    private JButton adicionarNovasSalas;
    private JButton VerDetalhes1;
    private JButton Editar1;
    private JButton Remover1;
    private JButton VerDetalhes2;
    private JButton Editar2;
    private JButton Remover2;
    private JButton VerDetalhes3;
    private JButton Editar3;
    private JButton Remover3;

    public SalasMainPage() {
        super("Página de Salas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(salasMainPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        Editar1.addActionListener(e -> new Editar());
        VerDetalhes1.addActionListener(e -> new Detalhes());
        adicionarNovasSalas.addActionListener(e -> new AdicionarNovasSalas());


    }
}
