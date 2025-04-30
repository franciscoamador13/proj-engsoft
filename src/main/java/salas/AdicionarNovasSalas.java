package salas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AdicionarNovasSalas extends JFrame {
    private JButton confirmarButton;
    private JPanel adicionarSala;
    private JTextField nomeSala;
    private JTextField nrLinhas;
    private JTextField nrColunas;
    private JButton botaoAcessibilidade;
    private JComboBox<String> nivelProjecao;
    private JCheckBox acessívelACadeirantesCheckBox;
    private JComboBox<String> nivelSom;

    public AdicionarNovasSalas() {
        super("Menu Principal");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarSala);

        // Adicionar opções ao JComboBox
        DefaultComboBoxModel<String> tiposSala = new DefaultComboBoxModel<>();
        tiposSala.addElement("2D");
        tiposSala.addElement("3D");
        tiposSala.addElement("IMAX");
        nivelProjecao.setModel(tiposSala);

        DefaultComboBoxModel<String> niveisSom = new DefaultComboBoxModel<>();
        niveisSom.addElement("60 dB");
        niveisSom.addElement("70–75 dB");
        niveisSom.addElement("75–80 dB");
        niveisSom.addElement("90–105 dB");
        niveisSom.addElement("115 dB");
        nivelSom.setModel(niveisSom);


        botaoAcessibilidade.setEnabled(false);

        // Adicionar listener ao checkbox para controlar o estado do botão
        acessívelACadeirantesCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                botaoAcessibilidade.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        // Adicionar listener ao botão de acessibilidade
        botaoAcessibilidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaAcessibilidade();
            }
        });




        pack();
        setVisible(true);
    }

    private void abrirJanelaAcessibilidade() {
        try {
            // Obter número de linhas e colunas
            int linhas = Integer.parseInt(nrLinhas.getText());
            int colunas = Integer.parseInt(nrColunas.getText());

            // Verificar se os valores são válidos
            if (linhas <= 0 || colunas <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Por favor insira valores válidos para linhas e colunas (maiores que zero).",
                        "Erro de entrada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criar nova janela
            JFrame janelaAcessibilidade = new JFrame("Acessibilidade");
            janelaAcessibilidade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Criar painel com grid layout
            JPanel painelBotoes = new JPanel(new GridLayout(linhas, colunas, 5, 5));

            // Adicionar botões com letras e números
            for (int i = 0; i < linhas; i++) {
                char letra = (char) ('A' + i);
                for (int j = 0; j < colunas; j++) {
                    int numero = j + 1;
                    JButton botao = new JButton(letra + "" + numero);
                    painelBotoes.add(botao);
                }
            }

            // Configurar janela
            janelaAcessibilidade.add(painelBotoes);
            janelaAcessibilidade.pack();
            janelaAcessibilidade.setLocationRelativeTo(this);
            janelaAcessibilidade.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor insira valores numéricos válidos para linhas e colunas.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
