package vendas;

import javax.swing.*;

public class vendasMainPage extends JFrame {
    private JPanel mainPageVendas;
    private JComboBox comboBox1;
    private JTextField fieldPrecoDesconto;
    private JTextField fieldCondicao;
    private JButton adicionarDescontoButton;
    private JTextField fieldPrecoBilhete;
    private JButton alterarPreçoButton;
    private Bilhete bilheteAtual;

    public vendasMainPage() {
        super("Página de gestão de vendas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(mainPageVendas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        adicionarDescontoButton.addActionListener(e -> {
            String condicao = fieldPrecoDesconto.getText();
            String valorTexto = fieldCondicao.getText();

            if ( !condicao.isEmpty() && !valorTexto.isEmpty()) {
                double valorDouble = Double.parseDouble(valorTexto);
                Desconto desconto = new Desconto(condicao, valorDouble);
                comboBox1.addItem(desconto);
                fieldPrecoDesconto.setText("");
                fieldCondicao.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.");
            }
        });
    }
}