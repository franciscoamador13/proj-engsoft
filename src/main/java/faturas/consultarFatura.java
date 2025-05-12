package faturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class consultarFatura extends JFrame {
    private JPanel consultarFaturasPage;
    private JTable table1;
    private JButton verFaturaButton;
    private JComboBox comboBox1;
    private DefaultTableModel tableModel;

    public consultarFatura() {
        super("Consultar Fatura");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(consultarFaturasPage);

        // Definir as colunas
        String[] colunas = {"Nº Fatura", "Data", "Total", "Nº Cliente"};
        tableModel = new DefaultTableModel(colunas, 0);
        table1.setModel(tableModel);

        // Adicionar faturas de exemplo
        adicionarFatura("001", "2024-05-12", 45.50, "CLI001");
        adicionarFatura("002", "2024-05-13", 32.75, "CLI002");
        adicionarFatura("003", "2024-05-14", 67.20, "CLI003");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarFatura(String numFatura, String data, double total, String numCliente) {
        Object[] linha = {numFatura, data, total, numCliente};
        tableModel.addRow(linha);
    }
}