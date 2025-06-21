package restauracao;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

public class GerirStock extends JFrame {
    private JPanel gerirStockPanel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton confirmarButton;
    private JRadioButton adicionarRadioButton;
    private JRadioButton removerRadioButton;
    private barMainPage parentWindow;
    private DadosRestauracao dadosRestauracao;

    public GerirStock(barMainPage parent) {
        this.parentWindow = parent;
        this.dadosRestauracao = DadosRestauracao.getInstance();
        setContentPane(gerirStockPanel);
        setTitle("Gestão de Stock");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create button group for radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(adicionarRadioButton);
        group.add(removerRadioButton);

        // Populate combo box with products
        for (Produto p : dadosRestauracao.getProdutos()) {
            comboBox1.addItem(p.getNome());
        }

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Por favor selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String quantidade = textField1.getText();
                if (quantidade.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor insira uma quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantidadeInt = Integer.parseInt(quantidade);
                    if (quantidadeInt <= 0) {
                        JOptionPane.showMessageDialog(null, "A quantidade deve ser maior que 0.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String selectedProduct = (String) comboBox1.getSelectedItem();
                    for (Produto p : dadosRestauracao.getProdutos()) {
                        if (p.getNome().equals(selectedProduct)) {
                            if (removerRadioButton.isSelected() && quantidadeInt > p.getStock()) {
                                JOptionPane.showMessageDialog(null, "Stock insuficiente para remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (adicionarRadioButton.isSelected()) {
                                p.setStock(p.getStock() + quantidadeInt);
                                JOptionPane.showMessageDialog(null, "Stock adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                p.setStock(p.getStock() - quantidadeInt);
                                JOptionPane.showMessageDialog(null, "Stock removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            }

                            parentWindow.atualizarTabela();
                            dispose();
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pack();
        setVisible(true);
    }

}