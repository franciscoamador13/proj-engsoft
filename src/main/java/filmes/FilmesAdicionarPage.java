package filmes;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class FilmesAdicionarPage extends JFrame {
    private JPanel filmesAdicionarPage;
    private JButton adicionarButton;
    private JButton limparButton;
    private JTextField tituloField;
    private JTextField tipoField;
    private JTextField realizacaoField;
    private JTextField duracaoField;
    private JTextField dataLancamentoField;

    private DadosFilmes dadosFilmes;

    public FilmesAdicionarPage() {
        super("Adicionar Filme");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(filmesAdicionarPage);

        dadosFilmes = DadosFilmes.getInstance();
        setupButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupButtons() {
        adicionarButton.addActionListener(e -> {
            if (validarCampos()) {
                String titulo = tituloField.getText().trim();
                String tipo = tipoField.getText().trim();
                String realizacao = realizacaoField.getText().trim();
                String duracao = duracaoField.getText().trim();
                String dataLancamento = dataLancamentoField.getText().trim();

                if (dadosFilmes.existeFilme(titulo)) {
                    JOptionPane.showMessageDialog(this,
                            "Já existe um filme com este título!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Filme novoFilme = new Filme(tipo, realizacao, duracao, dataLancamento, titulo);
                    dadosFilmes.adicionarFilme(novoFilme);

                    JOptionPane.showMessageDialog(this,
                            "Filme adicionado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao adicionar filme: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limparButton.addActionListener(e -> limparCampos());
    }

    private boolean validarCampos() {
        if (tituloField.getText().trim().isEmpty()) {
            mostrarErro("O título é obrigatório!");
            tituloField.requestFocus();
            return false;
        }
        if (tipoField.getText().trim().isEmpty()) {
            mostrarErro("O tipo é obrigatório!");
            tipoField.requestFocus();
            return false;
        }
        if (realizacaoField.getText().trim().isEmpty()) {
            mostrarErro("A realização é obrigatória!");
            realizacaoField.requestFocus();
            return false;
        }
        if (duracaoField.getText().trim().isEmpty()) {
            mostrarErro("A duração é obrigatória!");
            duracaoField.requestFocus();
            return false;
        }
        if (dataLancamentoField.getText().trim().isEmpty()) {
            mostrarErro("A data de lançamento é obrigatória!");
            dataLancamentoField.requestFocus();
            return false;
        }
        return true;
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this,
                mensagem,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    private void limparCampos() {
        tituloField.setText("");
        tipoField.setText("");
        realizacaoField.setText("");
        duracaoField.setText("");
        dataLancamentoField.setText("");
        tituloField.requestFocus();
    }

}
