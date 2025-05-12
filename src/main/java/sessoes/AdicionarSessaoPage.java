package sessoes;

import javax.swing.*;

public class AdicionarSessaoPage extends JFrame {
    private JPanel sessoesAdicionarPage;
    private JCheckBox checkBox1;
    private JButton adicionarButton;
    private JList list1;
    private JList list2;

    public AdicionarSessaoPage() {
        super("Adicionar Sessão");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesAdicionarPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarButton.addActionListener(e -> {
            // Aqui você pode adicionar a lógica para adicionar a nova sessão
            JOptionPane.showMessageDialog(this, "Sessão adicionada com sucesso!");
            dispose(); // Fecha a janela após adicionar
        });
    }
}
