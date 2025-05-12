package estatisticas;

import javax.swing.*;

public class statsMainPage extends JFrame{
    private JButton limparButton;
    private JComboBox comboBox1;
    private JPanel statsPage;
    private JButton filtrarButton;
    private JLabel grafico;

    public statsMainPage() {
        super("Statísticas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(statsPage);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        //update graph when filtered
        filtrarButton.addActionListener(e -> {
            String selectedValue = (String) comboBox1.getSelectedItem();
            if (selectedValue != null) {
                // Update the graph based on the selected value
                // For example, you can set the text of the graph label to the selected value
                grafico.setText("Gráfico atualizado para: " + selectedValue);
            }
        });

        limparButton.addActionListener(e -> {
            comboBox1.setSelectedIndex(-1);
            grafico.setText("Selecionar Grafico Desejado");
        });
    }

}
