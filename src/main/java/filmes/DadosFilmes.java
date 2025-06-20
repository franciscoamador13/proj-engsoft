package filmes;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DadosFilmes {
    private static DadosFilmes instance = null;
    private static final String FICHEIRO_DADOS = "filmes.txt";

    private List<Filme> filmes = new ArrayList<>();

    private DadosFilmes() {
        carregarDados();
    }

    public static DadosFilmes getInstance() {
        if (instance == null) {
            instance = new DadosFilmes();
        }
        return instance;
    }

    private void carregarDados() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_DADOS))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_DADOS))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            Filme filme = parsearLinha(linha);
                            if (filme != null) {
                                filmes.add(filme);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private void gravarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_DADOS))) {
            for (Filme filme : filmes) {
                writer.println(filmeParaString(filme));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados: " + e.getMessage());
        }
    }

    private String filmeParaString(Filme filme) {
        return String.format("%s|%s|%s|%s|%s|%s",
            filme.getTipo(),
            filme.getRealizacao(),
            filme.getDuracao(),
            filme.getDataLancamento(),
            filme.getTitulo(),
            filme.isAtivo()
        );
    }

    private Filme parsearLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 5) {
                String tipo = partes[0];
                String realizacao = partes[1];
                String duracao = partes[2];
                String dataLancamento = partes[3];
                String titulo = partes[4];
                boolean ativo = partes.length >= 6 ? Boolean.parseBoolean(partes[5]) : true;
                
                return new Filme(tipo, realizacao, duracao, dataLancamento, titulo, ativo);
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha + " - " + e.getMessage());
        }
        return null;
    }

    public List<Filme> getFilmes() {
        return new ArrayList<>(filmes);
    }

    public List<Filme> getFilmesFiltrados(String filtro) {
        List<Filme> filmesFiltrados = new ArrayList<>();
        for (Filme filme : filmes) {
            if (filtro.equals("Todos") ||
                (filtro.equals("Ativo") && filme.isAtivo()) ||
                (filtro.equals("Inativo") && !filme.isAtivo())) {
                filmesFiltrados.add(filme);
            }
        }
        return filmesFiltrados;
    }

    public void adicionarFilme(Filme filme) {
        if (filme != null && !existeFilme(filme.getTitulo())) {
            filmes.add(filme);
            gravarDados();
        }
    }

    public void removerFilme(Filme filme) {
        if (filmes.remove(filme)) {
            gravarDados();
        }
    }

    public void removerFilme(String titulo) {
        filmes.removeIf(f -> f.getTitulo().equals(titulo));
        gravarDados();
    }

    public boolean existeFilme(String titulo) {
        return filmes.stream().anyMatch(f -> f.getTitulo().equals(titulo));
    }

    public void atualizarFilme(Filme filmeAtualizado) {
        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).getTitulo().equals(filmeAtualizado.getTitulo())) {
                filmes.set(i, filmeAtualizado);
                gravarDados();
                break;
            }
        }
    }

    public int getNumeroFilmes() {
        return filmes.size();
    }

    public void limparFilmes() {
        filmes.clear();
        gravarDados();
    }

    public Filme getFilmePorTitulo(String titulo) {
        return filmes.stream()
                .filter(f -> f.getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
    }
} 