package sessoes;

import salas.AdicionarNovasSalas;
import salas.Detalhes;
import salas.Editar;

import javax.swing.*;

public class SessoesMainPage extends JFrame{
    private JPanel sessoesMainPage;
    private JLabel title;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton filtrarButton;
    private JList list1;
    private JButton adicionarNovaSessaoButton;

    public SessoesMainPage() {
        super("Página de Sessoes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesMainPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        //Editar1.addActionListener(e -> new Editar());
        //VerDetalhes1.addActionListener(e -> new Detalhes());
        //adicionarNovasSalas.addActionListener(e -> new AdicionarNovasSalas());
    }
}
