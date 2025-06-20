package restauracao;

public class Produto {
    private String nome;
    private double preco;
    private int stock;
    private String tipo;
    
    public Produto(String nome, double preco, int stock, String tipo) {
        this.nome = nome;
        this.preco = preco;
        this.stock = stock;
        this.tipo = tipo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public int getStock() {
        return stock;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void adicionarStock(int quantidade) {
        this.stock += quantidade;
    }
    
    public void removerStock(int quantidade) {
        if (quantidade <= this.stock) {
            this.stock -= quantidade;
        }
    }
} 