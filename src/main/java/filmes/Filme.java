package filmes;

public class Filme {
    private String tipo;
    private String realizacao;
    private String duracao;
    private String dataLancamento;
    private String titulo;
    private boolean ativo;

    public Filme(String tipo, String realizacao, String duracao, String dataLancamento, String titulo) {
        validarTipo(tipo);
        validarRealizacao(realizacao);
        validarDuracao(duracao);
        validarDataLancamento(dataLancamento);
        validarTitulo(titulo);

        this.tipo = tipo;
        this.realizacao = realizacao;
        this.duracao = duracao;
        this.dataLancamento = dataLancamento;
        this.titulo = titulo;
        this.ativo = true;
    }

    public Filme(String tipo, String realizacao, String duracao, String dataLancamento, String titulo, boolean ativo) {
        validarTipo(tipo);
        validarRealizacao(realizacao);
        validarDuracao(duracao);
        validarDataLancamento(dataLancamento);
        validarTitulo(titulo);

        this.tipo = tipo;
        this.realizacao = realizacao;
        this.duracao = duracao;
        this.dataLancamento = dataLancamento;
        this.titulo = titulo;
        this.ativo = ativo;
    }

    // Getters e Setters com validações nos setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        validarTipo(tipo);
        this.tipo = tipo;
    }

    public String getRealizacao() {
        return realizacao;
    }

    public void setRealizacao(String realizacao) {
        validarRealizacao(realizacao);
        this.realizacao = realizacao;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        validarDuracao(duracao);
        this.duracao = duracao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        validarDataLancamento(dataLancamento);
        this.dataLancamento = dataLancamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        validarTitulo(titulo);
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

    //Validações para os atributos
    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
    }

    private void validarTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não pode ser nulo ou vazio");
        }
    }

    private void validarRealizacao(String realizacao) {
        if (realizacao == null || realizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Realização não pode ser nula ou vazia");
        }
    }

    private void validarDuracao(String duracao) {
        if (duracao == null || duracao.trim().isEmpty()) {
            throw new IllegalArgumentException("Duração não pode ser nula ou vazia");
        }
    }

    private void validarDataLancamento(String dataLancamento) {
        if (dataLancamento == null || dataLancamento.trim().isEmpty()) {
            throw new IllegalArgumentException("Data de lançamento não pode ser nula ou vazia");
        }
    }
}
