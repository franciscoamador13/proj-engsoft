package vendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class alterarPrecoBilhete extends JDialog {
    private static alterarPrecoBilhete instance = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField precoField;

    private alterarPrecoBilhete() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        DadosVendas dadosVendas = DadosVendas.getInstance();
        precoField.setText(String.format("%.2f", dadosVendas.getPrecoBilhete()).replace(".", ","));

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

    public static alterarPrecoBilhete getInstance() {
        if (instance == null) {
            instance = new alterarPrecoBilhete();
        }
        return instance;
    }

    private void onOK() {
        try {
            double novoPreco = Double.parseDouble(precoField.getText().replace(",", "."));

            if (novoPreco <= 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosVendas dadosVendas = DadosVendas.getInstance();
            dadosVendas.setPrecoBilhete(novoPreco);
            
            JOptionPane.showMessageDialog(this, "Preço do bilhete alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}
