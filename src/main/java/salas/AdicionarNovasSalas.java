package salas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdicionarNovasSalas extends JFrame {
    private JButton confirmarButton, cancelarButton, botaoAcessibilidade;
    private JPanel adicionarSala;
    private JTextField nomeSala, nrLinhas, nrColunas;
    private JComboBox<String> nivelProjecao, nivelSom;
    private JCheckBox acessívelACadeirantesCheckBox;
    private SalasMainPage mainPage;
    private List<String> lugaresAcessiveis = new ArrayList<>();
    private DadosSalas dadosSalas; // Referência ao singleton

    public AdicionarNovasSalas(SalasMainPage mainPage) {
        super("Adicionar nova Sala");
        this.mainPage = mainPage;
        this.dadosSalas = DadosSalas.getInstance(); // Obter instância do singleton

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(adicionarSala);
        setValues();
        criarListeners();
        pack();
        setVisible(true);
    }

    private void setValues() {
        nivelProjecao.setModel(new DefaultComboBoxModel<>(new String[]{"2D", "3D", "IMAX"}));
        nivelSom.setModel(new DefaultComboBoxModel<>(new String[]{"60 dB", "70–75 dB", "75–80 dB", "90–105 dB", "115 dB"}));
        botaoAcessibilidade.setEnabled(false);
    }

    private void criarListeners() {
        acessívelACadeirantesCheckBox.addItemListener(e -> {
            boolean selected = e.getStateChange() == 1;
            botaoAcessibilidade.setEnabled(selected);
            if (!selected) lugaresAcessiveis.clear();
        });

        botaoAcessibilidade.addActionListener(e -> abrirJanelaAcessibilidade());
        confirmarButton.addActionListener(e -> criarSala());
        cancelarButton.addActionListener(e -> dispose());
    }

    private void criarSala() {
        try {
            String nome = nomeSala.getText().trim();
            if (nome.isEmpty()) {
                showError("Por favor insira o nome da sala.", "Campo obrigatório");
                return;
            }


            if (dadosSalas.existeSala(nome)) {
                showError("Já existe uma sala com o nome '" + nome + "'. Por favor escolha outro nome.", "Nome duplicado");
                return;
            }

            int linhas = Integer.parseInt(nrLinhas.getText());
            int colunas = Integer.parseInt(nrColunas.getText());

            if (linhas <= 0 || linhas > 26 || colunas <= 0 || colunas > 26) {
                showError("Por favor insira valores válidos para linhas e colunas (maiores que zero e menores ou iguais a 26).", "Erro de entrada");
                return;
            }

            boolean acessivel = acessívelACadeirantesCheckBox.isSelected();
            if (acessivel && lugaresAcessiveis.isEmpty()) {
                showError("Selecione os lugares acessíveis ou desmarque a opção de acessibilidade.", "Erro");
                return;
            }

            Sala novaSala = new Sala(nome, (String) nivelProjecao.getSelectedItem(),
                    (String) nivelSom.getSelectedItem(), linhas, colunas, acessivel);

            if (acessivel) {
                lugaresAcessiveis.sort((a, b) -> {
                    if (a.charAt(0) != b.charAt(0)) return Character.compare(a.charAt(0), b.charAt(0));
                    return Integer.compare(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1)));
                });
                novaSala.getLugaresAcessiveis().addAll(lugaresAcessiveis);
            }


            dadosSalas.adicionarSala(novaSala);


            if (mainPage != null) {
                mainPage.adicionarSala(novaSala);
            }

            JOptionPane.showMessageDialog(this,
                    "Sala '" + nome + "' criada com sucesso!\n" +
                            "Total de salas: " + dadosSalas.getNumeroSalas(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (NumberFormatException ex) {
            showError("Por favor insira valores numéricos válidos para linhas e colunas.", "Erro");
        }
    }

    private void abrirJanelaAcessibilidade() {
        try {
            int linhas = Integer.parseInt(nrLinhas.getText());
            int colunas = Integer.parseInt(nrColunas.getText());

            if (linhas <= 0 || colunas <= 0) {
                showError("Por favor insira valores válidos para linhas e colunas (maiores que zero).", "Erro de entrada");
                return;
            }

            JFrame janela = new JFrame("Selecionar Lugares Acessíveis");
            janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel painelBotoes = new JPanel(new GridLayout(linhas, colunas, 5, 5));

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    String posicao = (char)('A' + i) + "" + (j + 1);
                    JToggleButton botao = new JToggleButton(posicao);
                    botao.setSelected(lugaresAcessiveis.contains(posicao));

                    botao.addActionListener(e -> {
                        if (botao.isSelected()) {
                            if (!lugaresAcessiveis.contains(posicao)) lugaresAcessiveis.add(posicao);
                        } else {
                            lugaresAcessiveis.remove(posicao);
                        }
                    });

                    painelBotoes.add(botao);
                }
            }

            JPanel painelControle = new JPanel(new FlowLayout());
            JButton btnConfirmar = new JButton("Confirmar");
            JButton btnCancelar = new JButton("Cancelar");

            btnConfirmar.addActionListener(e -> {
                janela.dispose();
                String msg = lugaresAcessiveis.isEmpty() ? "Nenhum lugar acessível foi selecionado." :
                        "Lugares acessíveis selecionados:\n" + String.join(", ", lugaresAcessiveis);
                JOptionPane.showMessageDialog(this, msg, "Lugares Selecionados", JOptionPane.INFORMATION_MESSAGE);
            });

            btnCancelar.addActionListener(e -> janela.dispose());

            painelControle.add(btnConfirmar);
            painelControle.add(btnCancelar);

            janela.setLayout(new BorderLayout());
            janela.add(painelBotoes, BorderLayout.CENTER);
            janela.add(painelControle, BorderLayout.SOUTH);

            janela.pack();
            janela.setLocationRelativeTo(this);
            janela.setVisible(true);

        } catch (NumberFormatException ex) {
            showError("Por favor insira valores numéricos válidos para linhas e colunas.", "Erro");
        }
    }

    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}