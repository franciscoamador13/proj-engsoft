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

            JDialog janela = new JDialog(this, "Selecionar Lugares Acessíveis", true);
            janela.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            // Main panel with BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Title
            JLabel titleLabel = new JLabel("Selecione os lugares acessíveis");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // Grid panel for seats
            JPanel gridPanel = new JPanel(new GridLayout(linhas, colunas, 5, 5));
            List<JToggleButton> seatButtons = new ArrayList<>();

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    final String seatId = String.format("%c%d", (char)('A' + i), j + 1);
                    JToggleButton seatButton = new JToggleButton(seatId);
                    seatButton.setPreferredSize(new Dimension(50, 50));
                    seatButton.setFont(new Font("Arial", Font.PLAIN, 12));
                    
                    // Set initial state
                    boolean isSelected = lugaresAcessiveis.contains(seatId);
                    seatButton.setSelected(isSelected);
                    seatButton.setBackground(isSelected ? new Color(173, 216, 230) : new Color(220, 220, 220));

                    seatButton.addActionListener(e -> {
                        boolean selected = seatButton.isSelected();
                        seatButton.setBackground(selected ? new Color(173, 216, 230) : new Color(220, 220, 220));
                        if (selected) {
                            if (!lugaresAcessiveis.contains(seatId)) {
                                lugaresAcessiveis.add(seatId);
                            }
                        } else {
                            lugaresAcessiveis.remove(seatId);
                        }
                    });

                    seatButtons.add(seatButton);
                    gridPanel.add(seatButton);
                }
            }

            // Add grid panel to a scroll pane
            JScrollPane scrollPane = new JScrollPane(gridPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            JButton btnConfirmar = new JButton("Confirmar");
            JButton btnCancelar = new JButton("Cancelar");

            // Style buttons
            btnConfirmar.setPreferredSize(new Dimension(120, 40));
            btnCancelar.setPreferredSize(new Dimension(120, 40));
            btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 14));
            btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));

            btnConfirmar.addActionListener(e -> {
                janela.dispose();
                if (!lugaresAcessiveis.isEmpty()) {
                    lugaresAcessiveis.sort((a, b) -> {
                        if (a.charAt(0) != b.charAt(0)) return Character.compare(a.charAt(0), b.charAt(0));
                        return Integer.compare(Integer.parseInt(a.substring(1)), Integer.parseInt(b.substring(1)));
                    });
                    String msg = "Lugares acessíveis selecionados:\n" + String.join(", ", lugaresAcessiveis);
                    JOptionPane.showMessageDialog(this, msg, "Lugares Selecionados", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            btnCancelar.addActionListener(e -> {
                lugaresAcessiveis.clear();
                janela.dispose();
            });

            buttonPanel.add(btnConfirmar);
            buttonPanel.add(btnCancelar);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            // Legend panel
            JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            JLabel regularLabel = new JLabel("Regular");
            JLabel accessibleLabel = new JLabel("Acessível");

            JPanel regularColor = new JPanel();
            regularColor.setPreferredSize(new Dimension(20, 20));
            regularColor.setBackground(new Color(220, 220, 220));
            regularColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JPanel accessibleColor = new JPanel();
            accessibleColor.setPreferredSize(new Dimension(20, 20));
            accessibleColor.setBackground(new Color(173, 216, 230));
            accessibleColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            legendPanel.add(regularColor);
            legendPanel.add(regularLabel);
            legendPanel.add(Box.createHorizontalStrut(20));
            legendPanel.add(accessibleColor);
            legendPanel.add(accessibleLabel);

            mainPanel.add(legendPanel, BorderLayout.NORTH);

            janela.setContentPane(mainPanel);
            janela.setMinimumSize(new Dimension(600, 500));
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