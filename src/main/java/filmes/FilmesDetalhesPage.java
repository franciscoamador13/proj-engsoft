package filmes;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class FilmesDetalhesPage extends JFrame {
    private JPanel detalhesPanel;
    private JLabel tipoLabel;
    private JLabel realizacaoLabel;
    private JLabel duracaoLabel;
    private JLabel dataLancamentoLabel;
    private JLabel tituloLabel;
    private JButton editarButton;
    private JButton voltarButton;
    private JLabel estadoLabel;

    private DadosFilmes dadosFilmes;
    private String titulo;

    public FilmesDetalhesPage(String titulo) {
        this.titulo = titulo;
        this.dadosFilmes = DadosFilmes.getInstance();

        setContentPane(detalhesPanel);
        setTitle("Detalhes do Filme");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDadosFilme();
        setupButtons();

        pack();
    }

    private void carregarDadosFilme() {
        Filme filme = dadosFilmes.getFilmePorTitulo(titulo);
        if (filme != null) {
            tituloLabel.setText(filme.getTitulo());
            tipoLabel.setText(filme.getTipo());
            realizacaoLabel.setText(filme.getRealizacao());
            duracaoLabel.setText(filme.getDuracao());
            dataLancamentoLabel.setText(filme.getDataLancamento());
            estadoLabel.setText(filme.isAtivo() ? "Ativo" : "Inativo");
        }
    }

    private void setupButtons() {
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmesEditarDetalhesPage editarPage = new FilmesEditarDetalhesPage(titulo);
                editarPage.setVisible(true);
                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilmesMainPage mainPage = new FilmesMainPage();
                mainPage.setVisible(true);
                dispose();
            }
        });
    }

}
