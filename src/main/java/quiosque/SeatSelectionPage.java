package quiosque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import salas.DadosSalas;
import salas.Sala;
import sessoes.Sessao;

public class SeatSelectionPage extends JFrame {
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JButton confirmarButton;
    private JLabel titleLabel;
    private final Sessao sessao;
    private final Sala sala;
    private final List<JToggleButton> seatButtons;
    private String selectedSeat;

    public SeatSelectionPage(Sessao sessao) {
        super("Seleção de Lugar");
        System.out.println("Creating SeatSelectionPage for session: " + sessao.getTitulo()); // Debug print
        
        this.sessao = sessao;
        this.sala = DadosSalas.getInstance().getSalas().stream()
                .filter(s -> s.getNomeSala().equals(sessao.getSala()))
                .findFirst()
                .orElse(null);
        this.seatButtons = new ArrayList<>();
        
        System.out.println("Found sala: " + (sala != null ? sala.getNomeSala() : "null")); // Debug print
        
        if (sala == null) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar informações da sala.",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setupUI();
        createSeatGrid();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));
        pack();
        setLocationRelativeTo(null);
        
        // Make sure the window is visible and brought to front
        setVisible(true);
        toFront();
        requestFocus();
        
        System.out.println("SeatSelectionPage setup complete"); // Debug print
    }

    private void setupUI() {
        System.out.println("Setting up UI"); // Debug print
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        titleLabel = new JLabel("Selecione o seu lugar - Sala " + sala.getNomeSala());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create a panel for title and legend
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
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
        
        topPanel.add(legendPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Grid Panel
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(sala.getLinhas(), sala.getColunas(), 5, 5));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Confirm Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmarButton = new JButton("Confirmar");
        confirmarButton.setPreferredSize(new Dimension(200, 40));
        confirmarButton.setEnabled(false);
        confirmarButton.addActionListener(e -> {
            if (selectedSeat != null) {
                dispose();
            }
        });
        buttonPanel.add(confirmarButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void createSeatGrid() {
        System.out.println("Creating seat grid - Rows: " + sala.getLinhas() + ", Columns: " + sala.getColunas()); // Debug print
        List<String> lugaresAcessiveis = sala.getLugaresAcessiveis();
        ButtonGroup buttonGroup = new ButtonGroup(); // Only one seat can be selected

        for (int i = 0; i < sala.getLinhas(); i++) {
            for (int j = 0; j < sala.getColunas(); j++) {
                final String seatId = String.format("%c%d", (char)('A' + i), j + 1);
                JToggleButton seatButton = new JToggleButton();
                seatButton.setPreferredSize(new Dimension(50, 50));
                
                // Visual customization
                seatButton.setFont(new Font("Arial", Font.PLAIN, 12));
                seatButton.setText(seatId);
                
                // Check if it's an accessible seat
                boolean isAcessivel = lugaresAcessiveis.contains(seatId);
                if (isAcessivel) {
                    seatButton.setBackground(new Color(173, 216, 230)); // Light blue for accessible seats
                    seatButton.setToolTipText("Lugar acessível");
                } else {
                    seatButton.setBackground(new Color(220, 220, 220)); // Light gray for regular seats
                }

                seatButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedSeat = seatId;
                        confirmarButton.setEnabled(true);
                        System.out.println("Selected seat: " + selectedSeat); // Debug print
                    }
                });

                buttonGroup.add(seatButton);
                seatButtons.add(seatButton);
                gridPanel.add(seatButton);
            }
        }
        System.out.println("Seat grid created with " + seatButtons.size() + " buttons"); // Debug print
    }

    public String getSelectedSeat() {
        return selectedSeat;
    }
} 