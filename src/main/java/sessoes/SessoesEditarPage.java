package sessoes;

import javax.swing.*;

public class SessoesEditarPage extends JFrame {

    private JPanel sessoesEditarDetalhesPage;
    private JLabel filme_title;
    private JCheckBox checkBox1;
    private JLabel hora_data;
    private JLabel sala;
    private JButton confirmarButton;
    private JPanel sessoesEditar_detalhes;

    public SessoesEditarPage(JLabel title_sessao1, JLabel data1, JLabel filme1) {
        super("Editar Sessão " + title_sessao1.getText());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesEditarDetalhesPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        filme_title.setText(filme1.getText());
        hora_data.setText(data1.getText());

        confirmarButton.addActionListener(e -> {
            // Aqui você pode adicionar a lógica para confirmar as edições
            JOptionPane.showMessageDialog(this, "Sessão editada com sucesso!");
            dispose(); // Fecha a janela após confirmar
        });
    }
}
