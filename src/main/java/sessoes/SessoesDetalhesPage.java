package sessoes;

import javax.swing.*;

public class SessoesDetalhesPage extends JFrame {

    private JPanel sessoesDetalhesPage;
    private JButton editarButton;
    private JButton voltarButton;
    private JCheckBox ativaCheckBox;
    private JLabel filmeTitleLabel;
    private JLabel dataHoraLabel;
    private JLabel salaLabel;

    private DadosSessoes dadosSessoes;
    private String currentTitulo;
    private String currentData;
    private String currentHora;
    private String currentSala;

    public SessoesDetalhesPage(String titulo, String data, String hora, String sala) {
        super("Detalhes da Sessão");
        this.currentTitulo = titulo;
        this.currentData = data;
        this.currentHora = hora;
        this.currentSala = sala;
        
        dadosSessoes = DadosSessoes.getInstance();
        setContentPane(sessoesDetalhesPage);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load session data
        Sessao sessao = dadosSessoes.getSessao(titulo, data, hora, sala);
        if (sessao != null) {
            filmeTitleLabel.setText(sessao.getTitulo());
            dataHoraLabel.setText(sessao.getData() + " " + sessao.getHora());
            salaLabel.setText(sessao.getSala());
            ativaCheckBox.setSelected(sessao.isAtiva());
        }

        // Setup event handlers
        editarButton.addActionListener(e -> {
            new SessoesEditarPage(titulo, data, hora, sala);
            dispose();
        });

        voltarButton.addActionListener(e -> {
            dispose();
        });

        pack();
        setVisible(true);
    }
}
