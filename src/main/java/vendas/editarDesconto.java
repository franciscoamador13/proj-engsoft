package vendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class editarDesconto extends JDialog {
    private static editarDesconto instance = null;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton removerButton;
    private JTextField condicaoField;
    private JTextField valorField;
    private Desconto descontoOriginal;

    private editarDesconto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        removerButton.addActionListener(e -> onRemover());

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

    public static editarDesconto getInstance() {
        if (instance == null) {
            instance = new editarDesconto();
        }
        return instance;
    }

    public void setDesconto(Desconto desconto) {
        this.descontoOriginal = desconto;
        condicaoField.setText(desconto.getCondicao());
        valorField.setText(String.format("%.2f", desconto.getValor()).replace(".", ","));
    }

    private void onOK() {
        try {
            String novaCondicao = condicaoField.getText().trim();
            double novoValor = Double.parseDouble(valorField.getText().replace(",", "."));

            if (novaCondicao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A condição não pode estar vazia!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (novoValor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor do desconto em euros deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DadosVendas dadosVendas = DadosVendas.getInstance();
            double precoBilhete = dadosVendas.getPrecoBilhete();
            if (novoValor >= precoBilhete) {
                JOptionPane.showMessageDialog(this, 
                    String.format("O valor do desconto (%.2f€) não pode ser maior ou igual ao preço do bilhete (%.2f€)!", 
                    novoValor, precoBilhete), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!novaCondicao.equals(descontoOriginal.getCondicao()) && dadosVendas.existeDesconto(novaCondicao)) {
                JOptionPane.showMessageDialog(this, "Já existe um desconto com esta condição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dadosVendas.removerDesconto(descontoOriginal);
            Desconto novoDesconto = new Desconto(novaCondicao, novoValor);
            dadosVendas.adicionarDesconto(novoDesconto);

            JOptionPane.showMessageDialog(this, "Desconto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor do desconto inválido! Use um número válido em euros.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }

    private void onRemover() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover este desconto?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            DadosVendas.getInstance().removerDesconto(descontoOriginal);
            JOptionPane.showMessageDialog(this, "Desconto removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
