package salas;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private String nomeSala;
    private String nivelProjecao;
    private String nivelSom;
    private int linhas;
    private int colunas;
    private boolean acessivelCadeirantes;
    private List<String> lugaresAcessiveis;

    public Sala(String nomeSala, String nivelProjecao, String nivelSom, int linhas, int colunas, boolean acessivelCadeirantes) {
        this.nomeSala = nomeSala;
        this.nivelProjecao = nivelProjecao;
        this.nivelSom = nivelSom;
        this.linhas = linhas;
        this.colunas = colunas;
        this.acessivelCadeirantes = acessivelCadeirantes;
        this.lugaresAcessiveis = new ArrayList<>();
    }

    // Getters
    public String getNomeSala() { return nomeSala; }
    public String getNivelProjecao() { return nivelProjecao; }
    public String getNivelSom() { return nivelSom; }
    public int getLinhas() { return linhas; }
    public int getColunas() { return colunas; }
    public boolean isAcessivelCadeirantes() { return acessivelCadeirantes; }
    public List<String> getLugaresAcessiveis() { return lugaresAcessiveis; }

    // Setters
    public void setNomeSala(String nomeSala) { this.nomeSala = nomeSala; }
    public void setNivelProjecao(String nivelProjecao) { this.nivelProjecao = nivelProjecao; }
    public void setNivelSom(String nivelSom) { this.nivelSom = nivelSom; }
    public void setLinhas(int linhas) { this.linhas = linhas; }
    public void setColunas(int colunas) { this.colunas = colunas; }
    public void setAcessivelCadeirantes(boolean acessivelCadeirantes) { this.acessivelCadeirantes = acessivelCadeirantes; }

    // Método toString para exibição no JList
    @Override
    public String toString() {
        String resultado = nomeSala + " - " + nivelProjecao + " (" + linhas + "x" + colunas + ") - " + nivelSom;

        if (acessivelCadeirantes && !lugaresAcessiveis.isEmpty()) {
            resultado += " [Lugares Acessíveis: " + String.join(", ", lugaresAcessiveis) + "]";
        }

        return resultado;
    }
}