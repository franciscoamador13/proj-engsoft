package estatisticas;

import javax.swing.*;
import java.awt.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class statsMainPage extends JFrame {
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
            if (selectedValue != null && !selectedValue.isEmpty()) {
                // Map each filter option to its .txt file
                String txtPath = switch (selectedValue) {
                    case "Filmes Mais Vistos" -> "stats_filmes.txt";
                    case "Filmes Mais Vistos - Tipo" -> "stats_filmes_tipo.txt";
                    case "Filmes Mais Vistos - Faixa Etária" -> "stats_filmes_faixa.txt";
                    case "Salas Mais Frequentada - Tipo" -> "stats_salas.txt";
                    case "Produtos Mais Vendidos (Bar)" -> "stats_produtos.txt";
                    default -> "stats_filmes.txt";
                };
                updateChartFromTxt(grafico, txtPath);
            }
        });

        limparButton.addActionListener(e -> {
            comboBox1.setSelectedIndex(-1);
            grafico.setIcon(null); // Clear the graph
            grafico.setText("Selecionar Grafico Desejado");
        });
    }

    private void updateChartFromTxt(JLabel label, String txtPath) {
        try (BufferedReader br = new BufferedReader(new FileReader(txtPath))) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    dataset.addValue(Double.parseDouble(parts[1]), "", parts[0]);
                }
            }
            JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset);
            BufferedImage image = chart.createBufferedImage(600, 400);
            label.setIcon(new ImageIcon(image));
            label.setText(""); // Remove any text
        } catch (Exception ex) {
            label.setText("Erro ao carregar gráfico");
            label.setIcon(null);
        }
    }

}
