package faturas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DadosFaturas {
    private static DadosFaturas instance = null;
    private static final String FICHEIRO_DADOS = "faturas.txt";
    private List<Fatura> faturas;

    private DadosFaturas() {
        this.faturas = new ArrayList<>();
        carregarDados();
    }

    public static DadosFaturas getInstance() {
        if (instance == null) {
            instance = new DadosFaturas();
        }
        return instance;
    }

    private void carregarDados() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_DADOS))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_DADOS))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            Fatura fatura = parsearLinha(linha);
                            if (fatura != null) {
                                faturas.add(fatura);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados das faturas: " + e.getMessage());
        }
    }

    private void gravarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_DADOS))) {
            for (Fatura fatura : faturas) {
                writer.println(faturaParaString(fatura));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados das faturas: " + e.getMessage());
        }
    }

    private String faturaParaString(Fatura fatura) {
        StringBuilder sb = new StringBuilder();
        sb.append(fatura.getNumeroFatura()).append("|");
        sb.append(fatura.getNumeroCliente()).append("|");
        sb.append(fatura.getNif() != null ? fatura.getNif() : "").append("|");
        sb.append(fatura.getIdade() != null ? fatura.getIdade() : "").append("|");
        sb.append(fatura.getSessao()).append("|");
        sb.append(fatura.getSala()).append("|");
        sb.append(fatura.getData()).append("|");
        sb.append(fatura.getTotal()).append("|");

        // Converter linhas da fatura para string
        List<String> linhasString = new ArrayList<>();
        for (LinhaFatura linha : fatura.getLinhas()) {
            linhasString.add(linha.getDescricao() + ";" +
                    linha.getPrecoUnitario() + ";" +
                    linha.getQuantidade() + ";" +
                    linha.getSubtotal());
        }
        sb.append(String.join(":", linhasString));

        return sb.toString();
    }

    private Fatura parsearLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 8) {
                String numeroFatura = partes[0];
                String numeroCliente = partes[1];
                String nif = partes[2].isEmpty() ? null : partes[2];
                String idade = partes[3].isEmpty() ? null : partes[3];
                String sessao = partes[4];
                String sala = partes[5];
                String data = partes[6];
                double total = Double.parseDouble(partes[7]);


                Fatura fatura = new Fatura(numeroFatura, numeroCliente, nif, idade, sessao, sala, data);


                if (partes.length > 8 && !partes[8].trim().isEmpty()) {
                    String[] linhasString = partes[8].split(":");
                    for (String linhaString : linhasString) {
                        if (!linhaString.trim().isEmpty()) {
                            String[] partesLinha = linhaString.split(";");
                            if (partesLinha.length >= 4) {
                                String descricao = partesLinha[0];
                                double precoUnitario = Double.parseDouble(partesLinha[1]);
                                int quantidade = Integer.parseInt(partesLinha[2]);

                                LinhaFatura linhaFatura = new LinhaFatura(descricao, precoUnitario, quantidade);
                                fatura.getLinhas().add(linhaFatura);
                            }
                        }
                    }

                    fatura.calcularTotal();
                }

                return fatura;
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha da fatura: " + linha + " - " + e.getMessage());
        }
        return null;
    }

    public void adicionarFatura(Fatura fatura) {
        faturas.add(fatura);
        gravarDados();
    }

    public List<Fatura> getFaturas() {
        return new ArrayList<>(faturas);
    }

    public Fatura getFaturaPorNumero(String numeroFatura) {
        return faturas.stream()
                .filter(f -> f.getNumeroFatura().equals(numeroFatura))
                .findFirst()
                .orElse(null);
    }

    public List<Fatura> getFaturasPorNif(String nif) {
        return faturas.stream()
                .filter(f -> f.getNif() != null && f.getNif().equals(nif))
                .collect(Collectors.toList());
    }

    public List<Fatura> getFaturasPorCliente(String numeroCliente) {
        return faturas.stream()
                .filter(f -> f.getNumeroCliente().equals(numeroCliente))
                .collect(Collectors.toList());
    }

    public double getTotalVendas() {
        return faturas.stream()
                .mapToDouble(Fatura::getTotal)
                .sum();
    }

    public int getNumeroTotalFaturas() {
        return faturas.size();
    }

    public void limparFaturas() {
        faturas.clear();
        gravarDados();
    }

    public void removerFatura(String numeroFatura) {
        faturas.removeIf(fatura -> fatura.getNumeroFatura().equals(numeroFatura));
        gravarDados();
    }

    public void atualizarFatura(Fatura faturaAtualizada) {
        for (int i = 0; i < faturas.size(); i++) {
            if (faturas.get(i).getNumeroFatura().equals(faturaAtualizada.getNumeroFatura())) {
                faturas.set(i, faturaAtualizada);
                gravarDados();
                break;
            }
        }
    }

    // Método para criar uma nova fatura a partir dos dados do quiosque
    public Fatura criarNovaFatura(String nif, String idade, String sessao, String sala) {
        return new Fatura(nif, idade, sessao, sala);
    }
}