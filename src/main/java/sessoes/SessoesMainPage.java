package sessoes;

import javax.swing.*;

public class SessoesMainPage extends JFrame{
    private JPanel sessoesMainPage;
    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JButton limparButton;
    private JButton adicionarNovaSessaoButton;
    private JLabel title_sessao2;
    private JLabel title_sessao1;
    private JLabel title_sessao3;
    private JButton detalhesbtn1;
    private JLabel data1;
    private JLabel data2;
    private JLabel data3;
    private JLabel filme1;
    private JLabel filme2;
    private JLabel filme3;
    private JButton detalhesbtn2;
    private JButton detalhesbtn3;

    public SessoesMainPage() {
        super("Página de Sessoes");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesMainPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        detalhesbtn1.addActionListener(e -> {
            new SessoesDetalhesPage(title_sessao1, data1, filme1);
        });

        detalhesbtn2.addActionListener(e -> {
            new SessoesDetalhesPage(title_sessao2, data2, filme2);
        });

        detalhesbtn3.addActionListener(e -> {
            new SessoesDetalhesPage(title_sessao3, data3, filme3);
        });

        limparButton.addActionListener(e -> {
            comboBox1.setSelectedIndex(0);
            spinner1.setValue(0);
        });

        adicionarNovaSessaoButton.addActionListener(e -> new AdicionarSessaoPage());
    }
}
