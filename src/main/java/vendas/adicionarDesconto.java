package vendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class adicionarDesconto extends JDialog {
    private static adicionarDesconto instance = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField condicaoField;
    private JTextField valorField;

    private adicionarDesconto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static adicionarDesconto getInstance() {
        if (instance == null) {
            instance = new adicionarDesconto();
        }
        return instance;
    }

    private void onOK() {
        try {
            String condicao = condicaoField.getText().trim();
            double valor = Double.parseDouble(valorField.getText().replace(",", "."));

            if (condicao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A condição não pode estar vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor do desconto em euros deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosVendas dadosVendas = DadosVendas.getInstance();
            double precoBilhete = dadosVendas.getPrecoBilhete();
            if (valor >= precoBilhete) {
                JOptionPane.showMessageDialog(this, 
                    String.format("O valor do desconto (%.2f€) não pode ser maior ou igual ao preço do bilhete (%.2f€)!", 
                    valor, precoBilhete), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dadosVendas.existeDesconto(condicao)) {
                JOptionPane.showMessageDialog(this, "Já existe um desconto com esta condição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Desconto novoDesconto = new Desconto(condicao, valor);
            dadosVendas.adicionarDesconto(novoDesconto);
            
            condicaoField.setText("");
            valorField.setText("");
            
            JOptionPane.showMessageDialog(this, "Desconto adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor do desconto inválido! Use um número válido em euros.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        condicaoField.setText("");
        valorField.setText("");
        dispose();
    }
}
