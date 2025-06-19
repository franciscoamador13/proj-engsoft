import java.util.Date;

public class Filme {
    private String titulo;
    private String tipo;
    private String realizacao;
    private Long duracao;
    private String dataDeEstreia;

    public Filme(String titulo, String tipo, String realizacao, Long duracao, String dataDeEstreia) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.realizacao = realizacao;
        this.duracao = duracao;
        this.dataDeEstreia = dataDeEstreia;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRealizacao() {
        return realizacao;
    }

    public Long getDuracao() {
        return duracao;
    }

    public String getDataDeEstreia() {
        return dataDeEstreia;
    }

    @Override
    public String toString() {
        return "Filme{" +
                "titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", realizacao='" + realizacao + '\'' +
                ", duracao=" + duracao +
                ", dataDeEstreia=" + dataDeEstreia +
                '}';
    }
}