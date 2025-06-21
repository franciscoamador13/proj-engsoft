package faturas;

public class LinhaFatura {
    private String descricao;
    private double precoUnitario;
    private int quantidade;
    private double subtotal;

    public LinhaFatura(String descricao, double precoUnitario, int quantidade) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.subtotal = precoUnitario * quantidade;
    }


    public String getDescricao() {
        return descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getSubtotal() {
        return subtotal;
    }


    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subtotal = this.precoUnitario * quantidade;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
        this.subtotal = precoUnitario * this.quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f€ x %d = %.2f€",
                descricao, precoUnitario, quantidade, subtotal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LinhaFatura that = (LinhaFatura) obj;
        return Double.compare(that.precoUnitario, precoUnitario) == 0 &&
                quantidade == that.quantidade &&
                Double.compare(that.subtotal, subtotal) == 0 &&
                descricao.equals(that.descricao);
    }

    @Override
    public int hashCode() {
        int result = descricao.hashCode();
        result = 31 * result + Double.hashCode(precoUnitario);
        result = 31 * result + quantidade;
        result = 31 * result + Double.hashCode(subtotal);
        return result;
    }
}