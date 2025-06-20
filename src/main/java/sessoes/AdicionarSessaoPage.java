package sessoes;

import filmes.DadosFilmes;
import filmes.Filme;
import salas.DadosSalas;
import salas.Sala;

import javax.swing.*;
import javax.swing.DefaultListModel;
import java.util.List;

public class AdicionarSessaoPage extends JFrame {
    private JPanel sessoesAdicionarPage;
    private JCheckBox sessaoAtivaCheckBox;
    private JButton adicionarButton;
    private JButton limparButton;
    private JList<String> salasListBox;
    private JList<String> filmesListBox;
    private JTextField dataHoraTextField;

    private DadosFilmes dadosFilmes;
    private DadosSalas dadosSalas;
    private DefaultListModel<String> filmesListModel;
    private DefaultListModel<String> salasListModel;

    public AdicionarSessaoPage() {
        super("Adicionar Sessão");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(sessoesAdicionarPage);
        
        // Initialize data
        dadosFilmes = DadosFilmes.getInstance();
        dadosSalas = DadosSalas.getInstance();
        
        // Setup list models
        filmesListModel = new DefaultListModel<>();
        salasListModel = new DefaultListModel<>();
        filmesListBox.setModel(filmesListModel);
        salasListBox.setModel(salasListModel);
        
        // Load data
        carregarFilmes();
        carregarSalas();

        // Setup event handlers
        adicionarButton.addActionListener(e -> {
            if (validarCampos()) {
                adicionarSessao();
            }
        });

        limparButton.addActionListener(e -> limparCampos());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarFilmes() {
        filmesListModel.clear();
        List<Filme> filmes = dadosFilmes.getFilmesFiltrados("Ativo"); // Only load active movies
        for (Filme filme : filmes) {
            filmesListModel.addElement(filme.getTitulo());
        }
    }

    private void carregarSalas() {
        salasListModel.clear();
        List<Sala> salas = dadosSalas.getSalas();
        for (Sala sala : salas) {
            salasListModel.addElement(sala.getNomeSala());
        }
    }

    private boolean validarCampos() {
        if (filmesListBox.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um filme.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dataHoraTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira a data e hora.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (salasListBox.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma sala.", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void adicionarSessao() {
        String filme = filmesListBox.getSelectedValue();
        String dataHora = dataHoraTextField.getText().trim();
        String sala = salasListBox.getSelectedValue();
        boolean ativa = sessaoAtivaCheckBox.isSelected();

        // Split dataHora into data and hora
        String[] partes = dataHora.split(" ");
        if (partes.length != 2) {
            JOptionPane.showMessageDialog(this, "Formato de data e hora inválido. Use o formato: DD/MM/YYYY HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String data = partes[0];
        String hora = partes[1];

        // Create and add the session using DadosSessoes
        Sessao novaSessao = new Sessao(filme, data, hora, sala, ativa);
        DadosSessoes.getInstance().adicionarSessao(novaSessao);

        JOptionPane.showMessageDialog(this, "Sessão adicionada com sucesso!");
        dispose();
    }

    private void limparCampos() {
        filmesListBox.clearSelection();
        salasListBox.clearSelection();
        dataHoraTextField.setText("");
        sessaoAtivaCheckBox.setSelected(true);
    }
}
