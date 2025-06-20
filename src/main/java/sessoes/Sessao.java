package sessoes;

import java.util.Date;

public class Sessao {
    private String titulo;
    private String data;
    private String hora;
    private String sala;
    private boolean ativa;

    public Sessao(String titulo, String data, String hora, String sala, boolean ativa) {
        this.titulo = titulo;
        this.data = data;
        this.hora = hora;
        this.sala = sala;
        this.ativa = ativa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "titulo='" + titulo + '\'' +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", sala='" + sala + '\'' +
                ", ativa=" + ativa +
                '}';
    }
}
