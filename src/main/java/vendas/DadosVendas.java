package vendas;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DadosVendas {
    private static DadosVendas instance = null;
    private static final String FICHEIRO_DESCONTOS = "descontos.txt";
    private static final String FICHEIRO_PRECO = "preco_bilhete.txt";
    private static final double PRECO_PADRAO = 10.0; // Preço padrão caso não exista ficheiro

    private List<Desconto> descontos;
    private double precoBilhete;

    private DadosVendas() {
        descontos = new ArrayList<>();
        carregarDados();
    }

    public static DadosVendas getInstance() {
        if (instance == null) {
            instance = new DadosVendas();
        }
        return instance;
    }

    private void carregarDados() {
        carregarDescontos();
        carregarPrecoBilhete();
    }

    private void carregarDescontos() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_DESCONTOS))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_DESCONTOS))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            Desconto desconto = parsearLinhaDesconto(linha);
                            if (desconto != null) {
                                descontos.add(desconto);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar descontos: " + e.getMessage());
        }
    }

    private void carregarPrecoBilhete() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_PRECO))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_PRECO))) {
                    String linha = reader.readLine();
                    if (linha != null && !linha.trim().isEmpty()) {
                        precoBilhete = Double.parseDouble(linha.trim().replace(",", "."));
                    } else {
                        precoBilhete = PRECO_PADRAO;
                    }
                }
            } else {
                precoBilhete = PRECO_PADRAO;
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar preço do bilhete: " + e.getMessage());
            precoBilhete = PRECO_PADRAO;
        }
    }

    private void gravarDados() {
        gravarDescontos();
        gravarPrecoBilhete();
    }

    private void gravarDescontos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_DESCONTOS))) {
            for (Desconto desconto : descontos) {
                writer.println(descontoParaString(desconto));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar descontos: " + e.getMessage());
        }
    }

    private void gravarPrecoBilhete() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_PRECO))) {
            writer.println(String.format("%.2f", precoBilhete).replace(".", ","));
        } catch (IOException e) {
            System.err.println("Erro ao gravar preço do bilhete: " + e.getMessage());
        }
    }

    private String descontoParaString(Desconto desconto) {
        return String.format("%s|%.2f",
            desconto.getCondicao(),
            desconto.getValor()
        ).replace(".", ",");
    }

    private Desconto parsearLinhaDesconto(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 2) {
                String condicao = partes[0];
                double valor = Double.parseDouble(partes[1].replace(",", "."));
                return new Desconto(condicao, valor);
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha de desconto: " + linha + " - " + e.getMessage());
        }
        return null;
    }

    public List<Desconto> getDescontos() {
        return new ArrayList<>(descontos);
    }

    public void adicionarDesconto(Desconto desconto) {
        if (desconto != null && !existeDesconto(desconto.getCondicao())) {
            descontos.add(desconto);
            gravarDados();
        }
    }

    public void removerDesconto(Desconto desconto) {
        if (descontos.remove(desconto)) {
            gravarDados();
        }
    }

    public void removerDesconto(String condicao) {
        descontos.removeIf(d -> d.getCondicao().equals(condicao));
        gravarDados();
    }

    public boolean existeDesconto(String condicao) {
        return descontos.stream().anyMatch(d -> d.getCondicao().equals(condicao));
    }

    public void atualizarDesconto(Desconto descontoAtualizado) {
        for (int i = 0; i < descontos.size(); i++) {
            if (descontos.get(i).getCondicao().equals(descontoAtualizado.getCondicao())) {
                descontos.set(i, descontoAtualizado);
                gravarDados();
                break;
            }
        }
    }

    public double getPrecoBilhete() {
        return precoBilhete;
    }

    public void setPrecoBilhete(double novoPreco) {
        if (novoPreco > 0) {
            this.precoBilhete = novoPreco;
            gravarDados();
        }
    }

    public int getNumeroDescontos() {
        return descontos.size();
    }

    public void limparDescontos() {
        descontos.clear();
        gravarDados();
    }

    public Desconto getDescontoPorCondicao(String condicao) {
        return descontos.stream()
                .filter(d -> d.getCondicao().equals(condicao))
                .findFirst()
                .orElse(null);
    }
} 