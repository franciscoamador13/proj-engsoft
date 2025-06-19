import java.util.Date;

public class Sessao {
    private String nomeFilme;
    private Boolean ativo;
    private Long horaInicio;
    private String data;
    private Long salaId;

    public Sessao(String nomeFilme, Boolean ativo, Long horaInicio, String data, Long salaId) {
        this.nomeFilme = nomeFilme;
        this.ativo = ativo;
        this.horaInicio = horaInicio;
        this.data = data;
        this.salaId = salaId;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Long getHoraInicio() {
        return horaInicio;
    }

    public String getData() {
        return data;
    }

    public Long getSalaId() {
        return salaId;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "nomeFilme='" + nomeFilme + '\'' +
                ", ativo=" + ativo +
                ", horaInicio=" + horaInicio +
                ", data=" + data +
                ", salaId=" + salaId +
                '}';
    }

}
