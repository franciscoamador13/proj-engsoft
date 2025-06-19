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
    private List<String> lugaresAcessiveis = new ArrayList<>();

    // Construtor que recebe a sala já selecionada
    public editarSala(SalasMainPage mainPage, Sala salaParaEditar) {
        super("Editar Sala");
        this.mainPage = mainPage;
        this.salaAtual = salaParaEditar;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarSalaPage);
        setValues();
        criarListeners();

        // Carregar dados da sala selecionada
        carregarDadosSala();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Construtor original mantido para compatibilidade (caso seja chamado sem sala)
    public editarSala(SalasMainPage mainPage) {
        super("Editar Sala");
        this.mainPage = mainPage;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(editarSalaPage);
        setValues();
        criarListeners();

        // Primeiro, selecionar uma sala
        selecionarSala();

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

    private void selecionarSala() {
        List<Sala> salas = mainPage.getSalas();

        if (salas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há salas para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Criar array para o JList
        Sala[] salasArray = salas.toArray(new Sala[0]);

        // Mostrar diálogo de seleção
        Sala salaSelecionada = (Sala) JOptionPane.showInputDialog(
                this,
                "Selecione a sala para editar:",
                "Selecionar Sala",
                JOptionPane.QUESTION_MESSAGE,
                null,
                salasArray,
                salasArray[0]
        );

        if (salaSelecionada == null) {
            dispose();
            return;
        }

        this.salaAtual = salaSelecionada;
        carregarDadosSala();
    }

    private void carregarDadosSala() {
        nomeSala.setText(salaAtual.getNomeSala());
        nrLinhas.setText(String.valueOf(salaAtual.getLinhas()));
        nrColunas.setText(String.valueOf(salaAtual.getColunas()));
        nivelProjecao.setSelectedItem(salaAtual.getNivelProjecao());
        nivelSom.setSelectedItem(salaAtual.getNivelSom());
        acessívelACadeirantesCheckBox.setSelected(salaAtual.isAcessivelCadeirantes());

        // Copiar lugares acessíveis
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

            // Atualizar dados da sala usando getters/setters
            salaAtual.setNomeSala(nome);
            salaAtual.setLinhas(linhas);
            salaAtual.setColunas(colunas);
            salaAtual.setNivelProjecao((String) nivelProjecao.getSelectedItem());
            salaAtual.setNivelSom((String) nivelSom.getSelectedItem());
            salaAtual.setAcessivelCadeirantes(acessivel);

            // Atualizar lugares acessíveis
            salaAtual.getLugaresAcessiveis().clear();
            if (acessivel) {
                lugaresAcessiveis.sort((a, b) -> {
                    if (a.charAt(0) != b.charAt(0)) return Character.compare(a.charAt(0), b.charAt(0));
                    return Integer.compare(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1)));
                });
                salaAtual.getLugaresAcessiveis().addAll(lugaresAcessiveis);
            }

            // Atualizar lista na página principal
            mainPage.atualizarLista();

            JOptionPane.showMessageDialog(this, "Sala '" + nome + "' editada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
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

            JPanel painelControle = new JPanel();
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

            janela.add(new JPanel(new BorderLayout()) {{
                add(painelBotoes, BorderLayout.CENTER);
                add(painelControle, BorderLayout.SOUTH);
            }});

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