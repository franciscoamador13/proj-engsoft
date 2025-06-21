package quiosque;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

import restauracao.*;
import filmes.*;
import sessoes.*;
import vendas.*;
import faturas.*;

import java.awt.*;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class QuioskPage extends JFrame {
    private static QuioskPage instance = null;
    private JPanel quioskPage;
    private JList list2;
    private JRadioButton bilheteNormalRadioButton;
    private JRadioButton bilheteComDescontoRadioButton;
    private JComboBox comboBox1;
    private JList list1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton escolherLugarButton;
    private JList list3;
    private JButton confirmarButton;
    private JLabel precoBilheteLabel;
    private JLabel subtotalLabel;
    private JButton refreshButton;

    private DadosRestauracao dadosRestauracao;
    private DadosFilmes dadosFilmes;
    private DadosSessoes dadosSessoes;
    private DadosVendas dadosVendas;
    private DefaultListModel<String> sessoesListModel;
    private DefaultListModel<String> produtosListModel;
    private DefaultListModel<String> bundlesListModel;
    private DadosFaturas dadosFaturas;

    private String selectedSeat;

    private QuioskPage() {
        super("Página quiosque");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(quioskPage);

        // Initialize data managers
        dadosRestauracao = DadosRestauracao.getInstance();
        dadosFilmes = DadosFilmes.getInstance();
        dadosSessoes = DadosSessoes.getInstance();
        dadosVendas = DadosVendas.getInstance();
        dadosFaturas = DadosFaturas.getInstance();

        // Initialize list models
        sessoesListModel = new DefaultListModel<>();
        produtosListModel = new DefaultListModel<>();
        bundlesListModel = new DefaultListModel<>();

        // Setup UI components
        setupRadioButtons();
        setupComboBox();
        setupLists();
        setupButtons();
        setupPriceLabels();

        // Load data
        carregarDados();

        // Add window listener to reset data when closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                resetData();
                instance = null;
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static QuioskPage getInstance() {
        if (instance == null) {
            instance = new QuioskPage();
        }
        return instance;
    }

    private void setupRadioButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(bilheteNormalRadioButton);
        buttonGroup.add(bilheteComDescontoRadioButton);

        // Check if discounts are available
        boolean hasDiscounts = !dadosVendas.getDescontos().isEmpty();

        // Always select and show bilheteNormal
        bilheteNormalRadioButton.setSelected(true);
        bilheteNormalRadioButton.setVisible(true);

        // Only show discount option if discounts exist
        bilheteComDescontoRadioButton.setVisible(hasDiscounts);

        // Add action listeners for radio buttons to update subtotal
        bilheteNormalRadioButton.addActionListener(e -> updateSubtotal());
        bilheteComDescontoRadioButton.addActionListener(e -> updateSubtotal());
    }

    private void setupComboBox() {
        DefaultComboBoxModel<String> descontoModel = new DefaultComboBoxModel<>();

        for (Desconto desconto : dadosVendas.getDescontos()) {
            descontoModel.addElement(String.format("%s - %.2f€", desconto.getCondicao(), desconto.getValor()));
        }

        comboBox1.setModel(descontoModel);
        comboBox1.setEnabled(true);
        comboBox1.setVisible(!dadosVendas.getDescontos().isEmpty());

        // Add action listener to update subtotal when discount changes
        comboBox1.addActionListener(e -> {
            if (bilheteComDescontoRadioButton.isSelected()) {
                updateSubtotal();
            }
        });
    }

    private void setupLists() {
        list1.setModel(sessoesListModel);
        list2.setModel(produtosListModel);
        list3.setModel(bundlesListModel);

        // Set selection modes to allow deselection
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add list selection listeners for mutual exclusion and subtotal update
        list2.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (list2.getSelectedIndex() != -1) {
                    list3.clearSelection();
                }
                SwingUtilities.invokeLater(() -> updateSubtotal());
            }
        });

        list3.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (list3.getSelectedIndex() != -1) {
                    list2.clearSelection();
                }
                SwingUtilities.invokeLater(() -> updateSubtotal());
            }
        });

        // Add mouse listener to handle deselection on second click
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private long lastClickTime = 0;
            private int lastClickedIndex = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                JList<?> list = (JList<?>) e.getSource();
                int index = list.locationToIndex(e.getPoint());
                long currentTime = System.currentTimeMillis();

                // Check if this is a double click on the same item
                if (index == lastClickedIndex && (currentTime - lastClickTime) < 500) {
                    list.clearSelection();
                    SwingUtilities.invokeLater(() -> updateSubtotal());
                }

                lastClickedIndex = index;
                lastClickTime = currentTime;
            }
        };

        list2.addMouseListener(mouseAdapter);
        list3.addMouseListener(mouseAdapter);
    }

    private void setupButtons() {
        escolherLugarButton.addActionListener(e -> escolherLugar());
        confirmarButton.addActionListener(e -> confirmarCompra());
        refreshButton.addActionListener(e -> refreshData());
    }

    private void setupPriceLabels() {
        // Show fixed ticket price
        double fixedPrice = dadosVendas.getPrecoBilhete();
        precoBilheteLabel.setText(String.format("Preço do Bilhete: %.2f€", fixedPrice));

        // Initialize subtotal with ticket price
        updateSubtotal();
    }

    private void updateSubtotal() {
        double total = 0.0;

        // Add ticket price
        if (bilheteComDescontoRadioButton.isSelected() && comboBox1.isVisible() && comboBox1.getSelectedIndex() != -1) {
            // Get selected discount
            String selectedDiscount = comboBox1.getSelectedItem().toString();
            String[] parts = selectedDiscount.split(" - ");
            if (parts.length == 2) {
                String discountValue = parts[1].replace("€", "").trim();
                try {
                    total += Double.parseDouble(discountValue);
                } catch (NumberFormatException ex) {
                    total += dadosVendas.getPrecoBilhete();
                }
            }
        } else {
            total += dadosVendas.getPrecoBilhete();
            System.out.println("Added ticket price: " + dadosVendas.getPrecoBilhete());
        }

        // Add product price if selected
        if (list2.getSelectedValue() != null) {
            String selectedProduct = list2.getSelectedValue().toString();
            String[] parts = selectedProduct.split(" - ");
            if (parts.length == 2) {
                String priceStr = parts[1].replace("€", "").trim().replace(",", ".");
                try {
                    double productPrice = Double.parseDouble(priceStr);
                    total += productPrice;
                    System.out.println("Added product price: " + productPrice);
                } catch (NumberFormatException ex) {
                    System.out.println("Error parsing product price: " + priceStr);
                }
            }
        }

        // Add bundle price if selected
        if (list3.getSelectedValue() != null) {
            String selectedBundle = list3.getSelectedValue().toString();
            System.out.println("Selected bundle: " + selectedBundle);
            String[] parts = selectedBundle.split(" - ");
            if (parts.length == 2) {
                String priceStr = parts[1].replace("€", "").trim().replace(",", ".");
                try {
                    double bundlePrice = Double.parseDouble(priceStr);
                    total += bundlePrice;
                    System.out.println("Added bundle price: " + bundlePrice);
                } catch (NumberFormatException ex) {
                    System.out.println("Error parsing bundle price: " + priceStr);
                }
            }
        } else {
            System.out.println("No bundle selected");
        }

        System.out.println("Final total: " + total);

        // Update subtotal label
        subtotalLabel.setText(String.format("Subtotal: %.2f€", total));
    }

    private void refreshData() {
        // Clear existing data
        sessoesListModel.clear();
        selectedSeat = null;
        escolherLugarButton.setText("Escolher Lugar");

        // Reload sessions
        for (Sessao sessao : dadosSessoes.getSessoes()) {
            if (sessao.isAtiva()) {
                Filme filme = dadosFilmes.getFilmePorTitulo(sessao.getTitulo());
                if (filme != null && filme.isAtivo()) {
                    sessoesListModel.addElement(String.format("%s - %s %s (Sala %s)",
                            sessao.getTitulo(),
                            sessao.getData(),
                            sessao.getHora(),
                            sessao.getSala()));
                }
            }
        }

        // Clear selection
        list1.clearSelection();

        // Update UI
        updateSubtotal();
    }

    private void carregarDados() {
        // Clear existing data first
        sessoesListModel.clear();
        produtosListModel.clear();
        bundlesListModel.clear();

        // Carregar sessões disponíveis (sessões ativas)
        for (Sessao sessao : dadosSessoes.getSessoes()) {
            if (sessao.isAtiva()) {
                Filme filme = dadosFilmes.getFilmePorTitulo(sessao.getTitulo());
                if (filme != null && filme.isAtivo()) {
                    sessoesListModel.addElement(String.format("%s - %s %s (Sala %s)",
                            sessao.getTitulo(),
                            sessao.getData(),
                            sessao.getHora(),
                            sessao.getSala()));
                }
            }
        }

        // Carregar produtos
        for (Produto produto : dadosRestauracao.getProdutos()) {
            if (produto.getStock() > 0) {
                produtosListModel.addElement(produto.getNome() + " - " + String.format("%.2f€", produto.getPreco()));
            }
        }

        // Carregar bundles e esconder a seção se não houver bundles disponíveis
        List<Bundle> availableBundles = dadosRestauracao.getBundles();
        boolean hasBundles = false;
        for (Bundle bundle : availableBundles) {
            if (bundle.getStockAgrupado(dadosRestauracao) > 0) {
                bundlesListModel.addElement(bundle.getProdutosString() + " - " + String.format("%.2f€", bundle.getPreco()));
                hasBundles = true;
            }
        }

        // Hide or show the bundles section
        list3.setVisible(hasBundles);
        // Find and hide/show the bundles label and scrollpane
        Component[] components = quioskPage.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                Component[] panelComponents = panel.getComponents();
                for (Component panelComponent : panelComponents) {
                    // Hide/show the bundles label
                    if (panelComponent instanceof JLabel) {
                        JLabel label = (JLabel) panelComponent;
                        if (label.getText().equals("Bundles")) {
                            label.setVisible(hasBundles);
                        }
                    }
                    // Hide/show the scrollpane containing the bundles list
                    if (panelComponent instanceof JScrollPane) {
                        JScrollPane scrollPane = (JScrollPane) panelComponent;
                        if (scrollPane.getViewport().getView() == list3) {
                            scrollPane.setVisible(hasBundles);
                        }
                    }
                }
            }
        }
    }

    private void escolherLugar() {
        String sessaoSelecionada = list1.getSelectedValue() != null ? list1.getSelectedValue().toString() : null;
        if (sessaoSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione uma sessão primeiro.",
                    "Nenhuma sessão selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Selected session: " + sessaoSelecionada); // Debug print

        // Extract session information from the selected item
        Pattern pattern = Pattern.compile("(.*?) - (\\d{2}/\\d{2}/\\d{4}) (\\d{2}:\\d{2}) \\(Sala (.*)\\)");
        Matcher matcher = pattern.matcher(sessaoSelecionada);

        if (matcher.find()) {
            String titulo = matcher.group(1);
            String data = matcher.group(2);
            String hora = matcher.group(3);
            String sala = matcher.group(4);

            System.out.println("Parsed info - Title: " + titulo + ", Date: " + data + ", Time: " + hora + ", Room: " + sala); // Debug print

            Sessao sessao = dadosSessoes.getSessao(titulo, data, hora, sala);
            System.out.println("Found session: " + (sessao != null ? "yes" : "no")); // Debug print

            if (sessao != null) {
                SeatSelectionPage seatPage = new SeatSelectionPage(sessao);
                seatPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent windowEvent) {
                        String newSelectedSeat = seatPage.getSelectedSeat();
                        if (newSelectedSeat != null) {
                            selectedSeat = newSelectedSeat;
                            escolherLugarButton.setText("Lugar: " + selectedSeat + " (Clique para alterar)");
                        }
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao carregar informações da sessão.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Failed to parse session string"); // Debug print
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar informações da sessão.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetData() {
        // Clear selections
        list1.clearSelection();
        list2.clearSelection();
        list3.clearSelection();

        // Reset text fields
        textField1.setText("");
        textField2.setText("");

        // Reset radio buttons
        bilheteNormalRadioButton.setSelected(true);
        bilheteComDescontoRadioButton.setSelected(false);

        // Reset combo box
        comboBox1.setEnabled(false);
        comboBox1.setSelectedIndex(-1);

        // Reset seat selection
        selectedSeat = null;
        escolherLugarButton.setText("Escolher Lugar");
        escolherLugarButton.setEnabled(true);

        // Clear list models
        sessoesListModel.clear();
        produtosListModel.clear();
        bundlesListModel.clear();

        // Reset price labels
        updateSubtotal();
    }

    private void confirmarCompra() {
        if (list1.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione uma sessão.",
                    "Sessão não selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedSeat == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um lugar.",
                    "Lugar não selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nif = textField1.getText().trim();
        if (!nif.isEmpty() && !nif.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(this,
                    "O NIF deve conter 9 dígitos.",
                    "NIF inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idade = textField2.getText().trim();
        if (!idade.isEmpty()) {
            try {
                int idadeNum = Integer.parseInt(idade);
                if (idadeNum < 0 || idadeNum > 120) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira uma idade válida.",
                        "Idade inválida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (list2.getSelectedIndex() != -1 && list3.getSelectedIndex() != -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione apenas um produto OU um bundle, não ambos.",
                    "Seleção inválida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String sessaoSelecionada = list1.getSelectedValue().toString();
            String[] partesSessao = sessaoSelecionada.split(" - ");
            String tituloFilme = partesSessao[0];
            String dataHoraSala = partesSessao[1];

            Pattern pattern = Pattern.compile("\\(Sala (.*)\\)");
            Matcher matcher = pattern.matcher(dataHoraSala);
            String sala = matcher.find() ? matcher.group(1) : "N/A";

            Fatura novaFatura = dadosFaturas.criarNovaFatura(
                    nif.isEmpty() ? null : nif,
                    idade.isEmpty() ? null : idade,
                    sessaoSelecionada,
                    sala
            );

            if (bilheteComDescontoRadioButton.isSelected() && comboBox1.getSelectedIndex() != -1) {
                String descontoSelecionado = comboBox1.getSelectedItem().toString();
                String[] partesDesconto = descontoSelecionado.split(" - ");
                String tipoDesconto = partesDesconto[0];
                double precoDesconto = Double.parseDouble(partesDesconto[1].replace("€", "").trim());
                novaFatura.adicionarLinhaBilhete(tipoDesconto, precoDesconto);
            } else {
                novaFatura.adicionarLinhaBilhete(null, dadosVendas.getPrecoBilhete());
            }

            if (list2.getSelectedValue() != null) {
                String produtoSelecionado = list2.getSelectedValue().toString();
                String[] partesProduto = produtoSelecionado.split(" - ");
                String nomeProduto = partesProduto[0];
                double precoProduto = Double.parseDouble(partesProduto[1].replace("€", "").trim().replace(",", "."));
                novaFatura.adicionarLinha(nomeProduto, precoProduto, 1);
            }

            if (list3.getSelectedValue() != null) {
                String bundleSelecionado = list3.getSelectedValue().toString();
                String[] partesBundle = bundleSelecionado.split(" - ");
                String nomeBundle = partesBundle[0];
                double precoBundle = Double.parseDouble(partesBundle[1].replace("€", "").trim().replace(",", "."));
                novaFatura.adicionarLinha("Bundle: " + nomeBundle, precoBundle, 1);
            }

            dadosFaturas.adicionarFatura(novaFatura);

            JOptionPane.showMessageDialog(this,
                    "Compra realizada com sucesso!\n" +
                            "Número da fatura: " + novaFatura.getNumeroFatura() + "\n" +
                            "Total: " + String.format("%.2f€", novaFatura.getTotal()),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja ver os detalhes da fatura?",
                    "Ver Fatura",
                    JOptionPane.YES_NO_OPTION);

            if (opcao == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this,
                        novaFatura.toString(),
                        "Detalhes da Fatura " + novaFatura.getNumeroFatura(),
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar a compra: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        resetData();

        carregarDados();
    }

}
