package faturas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class verFatura extends JFrame {
    private JPanel verFaturaPage;
    private JTable table1;
    private JTextField nrClienteField;
    private JTextField nifField;
    private JTextField idadeField;
    private JTextField sessaoField;
    private JTextField totalField;
    private JTextField salaField;
    private JTextField dataField;
    private JTextField numeroFaturaField;
    private DefaultTableModel tableModel;

    public verFatura(Fatura fatura) {
        super("Ver Fatura - " + fatura.getNumeroFatura());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(verFaturaPage);

        String[] colunas = {"Produto", "Preço Unitário", "Quantidade", "Subtotal"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        preencherCampos(fatura);

        preencherTabela(fatura);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void preencherCampos(Fatura fatura) {
        if (numeroFaturaField != null) {
            numeroFaturaField.setText(fatura.getNumeroFatura());
        }

        nrClienteField.setText(fatura.getNumeroCliente());
        nifField.setText(fatura.getNif() != null ? fatura.getNif() : "N/A");
        idadeField.setText(fatura.getIdade() != null ? fatura.getIdade() : "N/A");
        sessaoField.setText(fatura.getSessao());
        salaField.setText(fatura.getSala());
        dataField.setText(fatura.getData());
        totalField.setText(String.format("%.2f€", fatura.getTotal()));

        nrClienteField.setEditable(false);
        nifField.setEditable(false);
        idadeField.setEditable(false);
        sessaoField.setEditable(false);
        salaField.setEditable(false);
        dataField.setEditable(false);
        totalField.setEditable(false);
        if (numeroFaturaField != null) {
            numeroFaturaField.setEditable(false);
        }
    }

    private void preencherTabela(Fatura fatura) {
        tableModel.setRowCount(0);

        for (LinhaFatura linha : fatura.getLinhas()) {
            Object[] dadosLinha = {
                    linha.getDescricao(),
                    String.format("%.2f€", linha.getPrecoUnitario()),
                    linha.getQuantidade(),
                    String.format("%.2f€", linha.getSubtotal())
            };
            tableModel.addRow(dadosLinha);
        }
    }
}