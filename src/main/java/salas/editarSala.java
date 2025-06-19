package salas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class editarSala extends JFrame {
    private JPanel editarSalaPage, editarSala;
    private JTextField nomeSala, nrLinhas, nrColunas;
    private JButton botaoAcessibilidade, confirmarEdiçãoButton;
    private JComboBox<String> nivelProjecao, nivelSom;
    private JCheckBox acessívelACadeirantesCheckBox;
    private JButton cancelarEdiçãoButton;

    private SalasMainPage mainPage;
    private Sala salaAtual;
    private String nomeOriginal;
    private List<String> lugaresAcessiveis = new ArrayList<>();
    private DadosSalas dadosSalas;


    public editarSala(SalasMainPage mainPage, Sala salaParaEditar) {
        super("Editar Sala");
        this.mainPage = mainPage;
        this.salaAtual = salaParaEditar;
        this.nomeOriginal = salaParaEditar.getNomeSala();
        this.dadosSalas = DadosSalas.getInstance();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarSalaPage);
        setValues();
        criarListeners();


        carregarDadosSala();

        pack();
        setLocationRelativeTo(null);
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
        confirmarEdiçãoButton.addActionListener(e -> confirmarEdicao());
        cancelarEdiçãoButton.addActionListener(e -> dispose());
    }



    private void carregarDadosSala() {
        nomeSala.setText(salaAtual.getNomeSala());
        nrLinhas.setText(String.valueOf(salaAtual.getLinhas()));
        nrColunas.setText(String.valueOf(salaAtual.getColunas()));
        nivelProjecao.setSelectedItem(salaAtual.getNivelProjecao());
        nivelSom.setSelectedItem(salaAtual.getNivelSom());
        acessívelACadeirantesCheckBox.setSelected(salaAtual.isAcessivelCadeirantes());

        lugaresAcessiveis.clear();
        lugaresAcessiveis.addAll(salaAtual.getLugaresAcessiveis());

        botaoAcessibilidade.setEnabled(salaAtual.isAcessivelCadeirantes());
    }

    private void confirmarEdicao() {
        try {
            String nome = nomeSala.getText().trim();
            if (nome.isEmpty()) {
                showError("Por favor insira o nome da sala.", "Campo obrigatório");
                return;
            }


            if (!nome.equals(nomeOriginal) && dadosSalas.existeSala(nome)) {
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


            salaAtual.setNomeSala(nome);
            salaAtual.setLinhas(linhas);
            salaAtual.setColunas(colunas);
            salaAtual.setNivelProjecao((String) nivelProjecao.getSelectedItem());
            salaAtual.setNivelSom((String) nivelSom.getSelectedItem());
            salaAtual.setAcessivelCadeirantes(acessivel);


            salaAtual.getLugaresAcessiveis().clear();
            if (acessivel) {
                lugaresAcessiveis.sort((a, b) -> {
                    if (a.charAt(0) != b.charAt(0)) return Character.compare(a.charAt(0), b.charAt(0));
                    return Integer.compare(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1)));
                });
                salaAtual.getLugaresAcessiveis().addAll(lugaresAcessiveis);
            }


            dadosSalas.atualizarSala(salaAtual);


            if (mainPage != null) {
                mainPage.atualizarLista();
            }

            JOptionPane.showMessageDialog(this,
                    "Sala '" + nome + "' editada com sucesso!\n" +
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