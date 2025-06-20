package sessoes;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DadosSessoes {
    private static DadosSessoes instance = null;
    private static final String FICHEIRO_DADOS = "sessoes.txt";

    private List<Sessao> sessoes = new ArrayList<>();

    private DadosSessoes() {
        carregarDados();
    }

    public static DadosSessoes getInstance() {
        if (instance == null) {
            instance = new DadosSessoes();
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
                            Sessao sessao = parsearLinha(linha);
                            if (sessao != null) {
                                sessoes.add(sessao);
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
            for (Sessao sessao : sessoes) {
                writer.println(sessaoParaString(sessao));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados: " + e.getMessage());
        }
    }

    private String sessaoParaString(Sessao sessao) {
        return String.format("%s|%s|%s|%s|%s",
            sessao.getTitulo(),
            sessao.getData(),
            sessao.getHora(),
            sessao.getSala(),
            sessao.isAtiva()
        );
    }

    private Sessao parsearLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 4) {
                String titulo = partes[0];
                String data = partes[1];
                String hora = partes[2];
                String sala = partes[3];
                boolean ativa = partes.length >= 5 ? Boolean.parseBoolean(partes[4]) : true;
                
                return new Sessao(titulo, data, hora, sala, ativa);
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha + " - " + e.getMessage());
        }
        return null;
    }

    public List<Sessao> getSessoes() {
        return new ArrayList<>(sessoes);
    }

    public List<Sessao> getSessoesFiltradas(String filtroFilme, String filtroData) {
        List<Sessao> sessoesFiltradas = new ArrayList<>();
        for (Sessao sessao : sessoes) {
            boolean matchFilme = filtroFilme.equals("Todos") || sessao.getTitulo().equals(filtroFilme);
            boolean matchData = filtroData == null || filtroData.isEmpty() || sessao.getData().equals(filtroData);
            
            if (matchFilme && matchData) {
                sessoesFiltradas.add(sessao);
            }
        }
        return sessoesFiltradas;
    }

    public void adicionarSessao(Sessao sessao) {
        if (sessao != null) {
            sessoes.add(sessao);
            gravarDados();
        }
    }

    public void removerSessao(Sessao sessao) {
        if (sessoes.remove(sessao)) {
            gravarDados();
        }
    }

    public void removerSessao(String titulo, String data, String hora, String sala) {
        sessoes.removeIf(s -> s.getTitulo().equals(titulo) && 
                             s.getData().equals(data) && 
                             s.getHora().equals(hora) && 
                             s.getSala().equals(sala));
        gravarDados();
    }

    public boolean existeSessao(String titulo, String data, String hora, String sala) {
        return sessoes.stream().anyMatch(s -> 
            s.getTitulo().equals(titulo) && 
            s.getData().equals(data) && 
            s.getHora().equals(hora) && 
            s.getSala().equals(sala));
    }

    public void atualizarSessao(Sessao sessaoAtualizada) {
        for (int i = 0; i < sessoes.size(); i++) {
            Sessao sessao = sessoes.get(i);
            if (sessao.getTitulo().equals(sessaoAtualizada.getTitulo()) &&
                sessao.getData().equals(sessaoAtualizada.getData()) &&
                sessao.getHora().equals(sessaoAtualizada.getHora()) &&
                sessao.getSala().equals(sessaoAtualizada.getSala())) {
                sessoes.set(i, sessaoAtualizada);
                gravarDados();
                break;
            }
        }
    }

    public int getNumeroSessoes() {
        return sessoes.size();
    }

    public void limparSessoes() {
        sessoes.clear();
        gravarDados();
    }

    public Sessao getSessao(String titulo, String data, String hora, String sala) {
        return sessoes.stream()
                .filter(s -> s.getTitulo().equals(titulo) &&
                            s.getData().equals(data) &&
                            s.getHora().equals(hora) &&
                            s.getSala().equals(sala))
                .findFirst()
                .orElse(null);
    }
} 