package restauracao;

import java.util.ArrayList;
import java.util.List;

public class Bundle {
    private List<String> produtosNomes;
    private double preco;
    private String tipo;
    
    public Bundle(List<String> produtosNomes, double preco, String tipo) {
        this.produtosNomes = new ArrayList<>(produtosNomes);
        this.preco = preco;
        this.tipo = tipo;
    }
    
    public List<String> getProdutosNomes() {
        return new ArrayList<>(produtosNomes);
    }
    
    public String getProdutosString() {
        return String.join(" + ", produtosNomes);
    }
    
    public double getPreco() {
        return preco;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getStockAgrupado(DadosRestauracao dados) {
        int minStock = Integer.MAX_VALUE;
        for (String nomeProduto : produtosNomes) {
            for (Produto produto : dados.getProdutos()) {
                if (produto.getNome().equals(nomeProduto)) {
                    minStock = Math.min(minStock, produto.getStock());
                    break;
                }
            }
        }
        return minStock == Integer.MAX_VALUE ? 0 : minStock;
    }
} 