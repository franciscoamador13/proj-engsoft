package faturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class verFatura extends JFrame {
    private JPanel verFaturaPage;
    private JTable table1;
    private JTextField a001TextField;
    private JTextField a123456789TextField;
    private JTextField a20TextField;
    private JTextField filmeDaAbelha19hTextField;
    private JTextField a24€TextField;
    private JTextField a2TextField;
    private DefaultTableModel tableModel;

    public verFatura() {
        super("Ver Fatura");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(verFaturaPage);

        String[] colunas = {"Produto", "Total por unidade", "Quantidade", "SubTotal"};
        tableModel = new DefaultTableModel(colunas, 0);
        table1.setModel(tableModel);


        adicionarCamposFatura("Pipoca grande", 2.50, 2, 5);
        adicionarCamposFatura("Coca cola 25ml", 1.50, 2, 3);
        adicionarCamposFatura("Bilhete normal", 8, 2, 16);


        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarCamposFatura(String produto, double totalunit, Integer quantidade, double subtotal) {
        Object[] linha = {produto, totalunit, quantidade, subtotal};
        tableModel.addRow(linha);
    }


}
