package faturas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private String numeroFatura;
    private String numeroCliente;
    private String nif;
    private String idade;
    private String sessao;
    private String sala;
    private String data;
    private List<LinhaFatura> linhas;
    private double total;
    private static int contadorFaturas = 1;

    // Construtor para criar nova fatura (usado no quiosque)
    public Fatura(String nif, String idade, String sessao, String sala) {
        this.numeroFatura = gerarNumeroFatura();
        this.numeroCliente = gerarNumeroCliente();
        this.nif = nif;
        this.idade = idade;
        this.sessao = sessao;
        this.sala = sala;
        this.data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        this.linhas = new ArrayList<>();
        this.total = 0.0;
    }

    // Construtor para carregar fatura do arquivo
    public Fatura(String numeroFatura, String numeroCliente, String nif, String idade,
                  String sessao, String sala, String data) {
        this.numeroFatura = numeroFatura;
        this.numeroCliente = numeroCliente;
        this.nif = nif;
        this.idade = idade;
        this.sessao = sessao;
        this.sala = sala;
        this.data = data;
        this.linhas = new ArrayList<>();
        this.total = 0.0;


        try {
            int numeroInt = Integer.parseInt(numeroFatura.substring(1));
            if (numeroInt >= contadorFaturas) {
                contadorFaturas = numeroInt + 1;
            }
        } catch (NumberFormatException e) {
            // Ignorar se não conseguir parsear
        }
    }

    private String gerarNumeroFatura() {
        return "F" + String.format("%05d", contadorFaturas++);
    }

    private String gerarNumeroCliente() {
        return "C" + String.format("%05d", System.currentTimeMillis() % 100000);
    }

    public void adicionarLinha(String descricao, double precoUnitario, int quantidade) {
        LinhaFatura linha = new LinhaFatura(descricao, precoUnitario, quantidade);
        linhas.add(linha);
        calcularTotal();
    }

    public void adicionarLinhaBilhete(String tipoDesconto, double preco) {
        String descricao = tipoDesconto != null ? "Bilhete (" + tipoDesconto + ")" : "Bilhete Normal";
        adicionarLinha(descricao, preco, 1);
    }

    public void removerLinha(int indice) {
        if (indice >= 0 && indice < linhas.size()) {
            linhas.remove(indice);
            calcularTotal();
        }
    }

    public void calcularTotal() {
        total = linhas.stream()
                .mapToDouble(LinhaFatura::getSubtotal)
                .sum();
    }


    public String getNumeroFatura() {
        return numeroFatura;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public String getNif() {
        return nif;
    }

    public String getIdade() {
        return idade;
    }

    public String getSessao() {
        return sessao;
    }

    public String getSala() {
        return sala;
    }

    public String getData() {
        return data;
    }

    public List<LinhaFatura> getLinhas() {
        return linhas;
    }

    public double getTotal() {
        return total;
    }


    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== FATURA ").append(numeroFatura).append(" ===\n");
        sb.append("Data: ").append(data).append("\n");
        sb.append("Cliente: ").append(numeroCliente).append("\n");
        if (nif != null && !nif.isEmpty()) {
            sb.append("NIF: ").append(nif).append("\n");
        }
        if (idade != null && !idade.isEmpty()) {
            sb.append("Idade: ").append(idade).append("\n");
        }
        sb.append("Sessão: ").append(sessao).append("\n");
        sb.append("Sala: ").append(sala).append("\n");
        sb.append("\n--- ITENS ---\n");

        for (LinhaFatura linha : linhas) {
            sb.append(linha.toString()).append("\n");
        }

        sb.append("\n--- TOTAL ---\n");
        sb.append("Total: ").append(String.format("%.2f€", total));

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Fatura fatura = (Fatura) obj;
        return numeroFatura.equals(fatura.numeroFatura);
    }

    @Override
    public int hashCode() {
        return numeroFatura.hashCode();
    }
}