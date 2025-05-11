package vendas;

public class Desconto {
    private String condicao;
    private Double valor;

    public Desconto( String condicao, Double valor) {
        this.condicao = condicao;
        this.valor = valor;
    }

    public String getCondicao() { return condicao; }
    public Double getValor() { return valor; }

    @Override
    public String toString() {
        return  condicao + " - " + valor + "€" ;
    }
}
