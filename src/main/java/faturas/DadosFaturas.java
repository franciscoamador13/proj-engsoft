package faturas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DadosFaturas {
    private static DadosFaturas instance = null;
    private List<Fatura> faturas;

    private DadosFaturas() {
        this.faturas = new ArrayList<>();
    }

    public static DadosFaturas getInstance() {
        if (instance == null) {
            instance = new DadosFaturas();
        }
        return instance;
    }

    public void adicionarFatura(Fatura fatura) {
        faturas.add(fatura);
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
    }

    // Método para criar uma nova fatura a partir dos dados do quiosque
    public Fatura criarNovaFatura(String nif, String idade, String sessao, String sala) {
        return new Fatura(nif, idade, sessao, sala);
    }
}