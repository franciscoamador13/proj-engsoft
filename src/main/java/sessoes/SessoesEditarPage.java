package sessoes;

import salas.DadosSalas;
import salas.Sala;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SessoesEditarPage extends JFrame {
    private JPanel sessoesEditarPage;
    private JButton confirmarButton;
    private JButton voltarButton;
    private JCheckBox ativaCheckBox;
    private JLabel filmeTitleLabel;
    private JTextField dataField;
    private JTextField horaField;
    private JComboBox<String> salaComboBox;

    private DadosSessoes dadosSessoes;
    private DadosSalas dadosSalas;
    private String currentTitulo;
    private String currentData;
    private String currentHora;
    private String currentSala;

    public SessoesEditarPage(String titulo, String data, String hora, String sala) {
        super("Editar Sessão");
        this.currentTitulo = titulo;
        this.currentData = data;
        this.currentHora = hora;
        this.currentSala = sala;

        dadosSessoes = DadosSessoes.getInstance();
        dadosSalas = DadosSalas.getInstance();

        setContentPane(sessoesEditarPage);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load session data
        Sessao sessao = dadosSessoes.getSessao(titulo, data, hora, sala);
        if (sessao != null) {
            filmeTitleLabel.setText(sessao.getTitulo());
            dataField.setText(sessao.getData());
            horaField.setText(sessao.getHora());
            ativaCheckBox.setSelected(sessao.isAtiva());

            // Load available rooms
            carregarSalas();
            salaComboBox.setSelectedItem(sessao.getSala());
        }

        // Setup event handlers
        confirmarButton.addActionListener(e -> {
            if (validarCampos()) {
                String novaData = dataField.getText().trim();
                String novaHora = horaField.getText().trim();
                String novaSala = (String) salaComboBox.getSelectedItem();
                boolean novoEstado = ativaCheckBox.isSelected();

                if (sessao != null) {
                    // Check if date/time/room combination is available
                    if (!novaData.equals(currentData) || !novaHora.equals(currentHora) || !novaSala.equals(currentSala)) {
                        if (dadosSessoes.existeSessao(currentTitulo, novaData, novaHora, novaSala)) {
                            JOptionPane.showMessageDialog(this,
                                    "Já existe uma sessão neste horário e sala.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Update session
                    sessao.setData(novaData);
                    sessao.setHora(novaHora);
                    sessao.setSala(novaSala);
                    sessao.setAtiva(novoEstado);

                    dadosSessoes.atualizarSessao(sessao);
                    JOptionPane.showMessageDialog(this,
                            "Sessão editada com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });

        voltarButton.addActionListener(e -> dispose());

        pack();
        setVisible(true);
    }

    private void carregarSalas() {
        salaComboBox.removeAllItems();
        List<Sala> salas = dadosSalas.getSalas();
        for (Sala sala : salas) {
            salaComboBox.addItem(sala.getNomeSala());
        }
    }

    private boolean validarCampos() {
        // Validate date format (dd/MM/yyyy)
        String data = dataField.getText().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(data);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida. Use o formato dd/MM/yyyy",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate time format (HH:mm)
        String hora = horaField.getText().trim();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setLenient(false);
        try {
            timeFormat.parse(hora);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Hora inválida. Use o formato HH:mm",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate room selection
        if (salaComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma sala.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

}
