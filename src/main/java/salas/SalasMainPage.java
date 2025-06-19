package salas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SalasMainPage extends JFrame {
    private JPanel salasMainPage;
    private JLabel title;
    private JButton adicionarNovasSalas;
    private JList<Sala> list1;
    private JButton editarSalaButton;
    private JButton eliminarSalaButton;

    // Lista para armazenar as salas
    private List<Sala> salas;
    private DefaultListModel<Sala> listModel;

    public SalasMainPage() {
        super("Página de Salas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(salasMainPage);

        // Inicializar lista de salas e modelo do JList
        salas = new ArrayList<>();
        listModel = new DefaultListModel<>();
        list1.setModel(listModel);

        // Configurar JList para seleção única
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurar listeners dos botões
        configurarListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configurarListeners() {
        // Botão adicionar sala
        adicionarNovasSalas.addActionListener(e -> new AdicionarNovasSalas(this));

        // Botão editar sala
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

        // Botão eliminar sala
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

        salas.remove(salaSelecionada);
        listModel.removeElement(salaSelecionada);
        JOptionPane.showMessageDialog(this,
                "Sala '" + salaSelecionada.getNomeSala() + "' eliminada com sucesso!",
                "Sala Eliminada",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para adicionar sala à lista
    public void adicionarSala(Sala sala) {
        salas.add(sala);
        listModel.addElement(sala);
    }

    // Método para obter sala selecionada
    public Sala getSalaSelecionada() {
        return list1.getSelectedValue();
    }

    // Método para obter todas as salas
    public List<Sala> getSalas() {
        return salas;
    }

    // Método para atualizar JList
    public void atualizarLista() {
        listModel.clear();
        for (Sala sala : salas) {
            listModel.addElement(sala);
        }
    }
}