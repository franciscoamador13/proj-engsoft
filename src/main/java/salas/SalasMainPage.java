package salas;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class SalasMainPage extends JFrame {
    private JPanel salasMainPage;
    private JLabel title;
    private JButton adicionarNovasSalas;
    private JList<Sala> list1;
    private JButton editarSalaButton;
    private JButton eliminarSalaButton;

    private DefaultListModel<Sala> listModel;
    private DadosSalas dadosSalas;

    public SalasMainPage() {
        super("Página de Salas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(salasMainPage);


        dadosSalas = DadosSalas.getInstance();


        listModel = new DefaultListModel<>();
        list1.setModel(listModel);


        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        carregarSalasNaLista();


        configurarListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarSalasNaLista() {
        listModel.clear();
        List<Sala> salas = dadosSalas.getSalas();
        for (Sala sala : salas) {
            listModel.addElement(sala);
        }
    }

    private void configurarListeners() {

        adicionarNovasSalas.addActionListener(e -> new AdicionarNovasSalas(this));


        editarSalaButton.addActionListener(e -> {
            Sala salaSelecionada = list1.getSelectedValue();
            if (salaSelecionada == null) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, selecione uma sala para editar.",
                        "Nenhuma sala selecionada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            new editarSala(this, salaSelecionada);
        });


        eliminarSalaButton.addActionListener(e -> eliminarSala());
    }

    private void eliminarSala() {
        Sala salaSelecionada = list1.getSelectedValue();

        if (salaSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione uma sala para eliminar.",
                    "Nenhuma sala selecionada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja eliminar a sala '" + salaSelecionada.getNomeSala() + "'?",
                "Confirmar Eliminação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {

            dadosSalas.removerSala(salaSelecionada);


            listModel.removeElement(salaSelecionada);

            JOptionPane.showMessageDialog(this,
                    "Sala '" + salaSelecionada.getNomeSala() + "' eliminada com sucesso!",
                    "Sala Eliminada",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void adicionarSala(Sala sala) {
        listModel.addElement(sala);
    }


    public Sala getSalaSelecionada() {
        return list1.getSelectedValue();
    }


    public List<Sala> getSalas() {
        return dadosSalas.getSalas();
    }


    public void atualizarLista() {
        carregarSalasNaLista();
    }


    public void atualizarSala(Sala salaAntiga, Sala salaNova) {

        dadosSalas.atualizarSala(salaNova);


        int index = listModel.indexOf(salaAntiga);
        if (index != -1) {
            listModel.setElementAt(salaNova, index);
        }
    }

}