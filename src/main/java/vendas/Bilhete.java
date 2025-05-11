package vendas;

public class Bilhete {
    private double precoFixo;
    private Desconto desconto;

    // Construtor básico
    public Bilhete(double precoFixo) {
        this.precoFixo = precoFixo;
        this.desconto = null;
    }

    // Construtor com desconto
    public Bilhete(double precoFixo, Desconto desconto) {
        this.precoFixo = precoFixo;
        this.desconto = desconto;
    }

    // Getters e setters
    public double getPrecoFixo() {
        return precoFixo;
    }

    public void setPrecoFixo(double precoFixo) {
        this.precoFixo = precoFixo;
    }

    public Desconto getDesconto() {
        return desconto;
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }

    // Calcula o preço final: se houver desconto, usa o preço com desconto
    public double getPrecoFinal() {
        if (desconto != null) {
            return desconto.getValor();
        }
        return precoFixo;
    }

    @Override
    public String toString() {
        return "Bilhete{" +
                "precoFixo=" + precoFixo +
                ", desconto=" + (desconto != null ? desconto.getCondicao() : "nenhum") +
                ", precoFinal=" + getPrecoFinal() +
                '}';
    }
}