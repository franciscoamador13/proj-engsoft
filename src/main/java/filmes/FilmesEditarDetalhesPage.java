package filmes;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class FilmesEditarDetalhesPage extends JFrame {
    private JPanel editarPanel;
    private JTextField tipoField;
    private JTextField realizacaoField;
    private JTextField duracaoField;
    private JTextField dataLancamentoField;
    private JTextField tituloField;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JCheckBox ativoCheckBox;

    private DadosFilmes dadosFilmes;
    private String titulo;

    public FilmesEditarDetalhesPage(String titulo) {
        this.titulo = titulo;
        this.dadosFilmes = DadosFilmes.getInstance();

        setContentPane(editarPanel);
        setTitle("Editar Filme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDadosFilme();
        setupButtons();

        pack();
    }

    private void carregarDadosFilme() {
        Filme filme = dadosFilmes.getFilmePorTitulo(titulo);
        if (filme != null) {
            tituloField.setText(filme.getTitulo());
            tituloField.setEnabled(false); // Título não pode ser editado
            tipoField.setText(filme.getTipo());
            realizacaoField.setText(filme.getRealizacao());
            duracaoField.setText(filme.getDuracao());
            dataLancamentoField.setText(filme.getDataLancamento());
            ativoCheckBox.setSelected(filme.isAtivo());
        }
    }

    private void setupButtons() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    Filme filmeAtualizado = new Filme(
                            tipoField.getText(),
                            realizacaoField.getText(),
                            duracaoField.getText(),
                            dataLancamentoField.getText(),
                            tituloField.getText(),
                            ativoCheckBox.isSelected()
                    );

                    dadosFilmes.atualizarFilme(filmeAtualizado);
                    JOptionPane.showMessageDialog(null, "Filme atualizado com sucesso!");

                    FilmesDetalhesPage detalhesPage = new FilmesDetalhesPage(titulo);
                    detalhesPage.setVisible(true);
                    dispose();
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmesDetalhesPage detalhesPage = new FilmesDetalhesPage(titulo);
                detalhesPage.setVisible(true);
                dispose();
            }
        });
    }

    private boolean validarCampos() {
        if (tipoField.getText().trim().isEmpty() ||
                realizacaoField.getText().trim().isEmpty() ||
                duracaoField.getText().trim().isEmpty() ||
                dataLancamentoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios!");
            return false;
        }
        return true;
    }

}