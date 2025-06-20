package filmes;

public class Filme {
    private String tipo;
    private String realizacao;
    private String duracao;
    private String dataLancamento;
    private String titulo;
    private boolean ativo;

    public Filme(String tipo, String realizacao, String duracao, String dataLancamento, String titulo) {
        this.tipo = tipo;
        this.realizacao = realizacao;
        this.duracao = duracao;
        this.dataLancamento = dataLancamento;
        this.titulo = titulo;
        this.ativo = true; // By default, new movies are active
    }

    public Filme(String tipo, String realizacao, String duracao, String dataLancamento, String titulo, boolean ativo) {
        this.tipo = tipo;
        this.realizacao = realizacao;
        this.duracao = duracao;
        this.dataLancamento = dataLancamento;
        this.titulo = titulo;
        this.ativo = ativo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRealizacao() {
        return realizacao;
    }

    public void setRealizacao(String realizacao) {
        this.realizacao = realizacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "tipo='" + tipo + '\'' +
                ", realizacao='" + realizacao + '\'' +
                ", duracao='" + duracao + '\'' +
                ", dataLancamento='" + dataLancamento + '\'' +
                ", titulo='" + titulo + '\'' +
                ", ativo=" + ativo +
                '}';
    }
} 