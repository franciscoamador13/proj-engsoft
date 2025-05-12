package sessoes;

import javax.swing.*;

public class SessoesDetalhesPage extends JFrame {

    private JPanel sessoesDetalhesPage;
    private JButton editarButton;
    private JCheckBox checkBox1;
    private JLabel filme_title;
    private JLabel hora_data;
    private JLabel sala;

    public SessoesDetalhesPage(JLabel title_sessao1, JLabel data1, JLabel filme1) {
        super("Detalhes da Sessão " + title_sessao1.getText() + " - " + filme1.getText());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesDetalhesPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Adiciona os valores dos parâmetros nos labels
        filme_title.setText(filme1.getText());
        hora_data.setText(data1.getText());

        editarButton.addActionListener(e -> {
            new SessoesEditarPage(title_sessao1, data1, filme1);
        });
    }
}
