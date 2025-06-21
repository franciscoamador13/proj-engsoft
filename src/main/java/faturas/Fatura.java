package faturas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private static int contadorNumeroFatura = 1;
    private static int contadorNumeroCliente = 1;

    private String numeroFatura;
    private String numeroCliente;
    private String nif;
    private String idade;
    private String sessao;
    private String sala;
    private String data;
    private double total;
    private List<LinhaFatura> linhas;

    public Fatura(String nif, String idade, String sessao, String sala) {
        this.numeroFatura = String.format("%03d", contadorNumeroFatura++);
        this.numeroCliente = "CLI" + String.format("%03d", contadorNumeroCliente++);
        this.nif = nif;
        this.idade = idade;
        this.sessao = sessao;
        this.sala = sala;
        this.data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.total = 0.0;
        this.linhas = new ArrayList<>();
    }

    public void adicionarLinha(String descricao, double precoUnitario, int quantidade) {
        LinhaFatura linha = new LinhaFatura(descricao, precoUnitario, quantidade);
        linhas.add(linha);
        calcularTotal();
    }

    public void adicionarLinhaBilhete(String tipoDesconto, double preco) {
        String descricao = tipoDesconto != null && !tipoDesconto.isEmpty() ?
                "Bilhete com desconto (" + tipoDesconto + ")" : "Bilhete normal";
        adicionarLinha(descricao, preco, 1);
    }

    private void calcularTotal() {
        total = linhas.stream()
                .mapToDouble(LinhaFatura::getSubtotal)
                .sum();
    }

    // Getters
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

    public double getTotal() {
        return total;
    }

    public List<LinhaFatura> getLinhas() {
        return new ArrayList<>(linhas);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fatura: ").append(numeroFatura).append("\n");
        sb.append("Cliente: ").append(numeroCliente).append("\n");
        sb.append("NIF: ").append(nif != null && !nif.isEmpty() ? nif : "N/A").append("\n");
        sb.append("Idade: ").append(idade != null && !idade.isEmpty() ? idade : "N/A").append("\n");
        sb.append("Sessão: ").append(sessao).append("\n");
        sb.append("Sala: ").append(sala).append("\n");
        sb.append("Data: ").append(data).append("\n");
        sb.append("Total: ").append(String.format("%.2f€", total)).append("\n");
        sb.append("Itens:\n");
        for (LinhaFatura linha : linhas) {
            sb.append("  ").append(linha.toString()).append("\n");
        }
        return sb.toString();
    }
}