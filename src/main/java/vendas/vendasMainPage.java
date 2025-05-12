package vendas;

import javax.swing.*;

public class vendasMainPage extends JFrame {
    private JPanel mainPageVendas;
    private JList list1;
    private JTextField textField1;
    private JButton adicionarDescontoButton;
    private JButton editarDescontosButton;
    private JButton removerDescontosButton;
    private JButton alterarPreçoFixoDoButton;
    private Bilhete bilheteAtual;

    public vendasMainPage() {
        super("Página de gestão de vendas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(mainPageVendas);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        alterarPreçoFixoDoButton.addActionListener(e -> {
            new alterarPrecoBilhete();
        });

        editarDescontosButton.addActionListener(e -> {
            new editarDesconto();
        });

        adicionarDescontoButton.addActionListener(e -> {
            new adicionarDesconto();
        });


    }

}