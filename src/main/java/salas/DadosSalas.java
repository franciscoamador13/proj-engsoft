package salas;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DadosSalas {
    private static DadosSalas instance = null;
    private static final String FICHEIRO_DADOS = "salas.txt";

    private List<Sala> salas = new ArrayList<>();

    private DadosSalas() {
        carregarDados();
    }

    public static DadosSalas getInstance() {
        if (instance == null) {
            instance = new DadosSalas();
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
                            Sala sala = parsearLinha(linha);
                            if (sala != null) {
                                salas.add(sala);
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
            for (Sala sala : salas) {
                writer.println(salaParaString(sala));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados: " + e.getMessage());
        }
    }

    private String salaParaString(Sala sala) {
        StringBuilder sb = new StringBuilder();
        sb.append(sala.getNomeSala()).append("|");
        sb.append(sala.getNivelProjecao()).append("|");
        sb.append(sala.getNivelSom()).append("|");
        sb.append(sala.getLinhas()).append("|");
        sb.append(sala.getColunas()).append("|");
        sb.append(sala.isAcessivelCadeirantes()).append("|");
        sb.append(String.join(",", sala.getLugaresAcessiveis()));
        return sb.toString();
    }

    private Sala parsearLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 6) {
                String nome = partes[0];
                String nivelProjecao = partes[1];
                String nivelSom = partes[2];
                int linhas = Integer.parseInt(partes[3]);
                int colunas = Integer.parseInt(partes[4]);
                boolean acessivel = Boolean.parseBoolean(partes[5]);

                Sala sala = new Sala(nome, nivelProjecao, nivelSom, linhas, colunas, acessivel);


                if (partes.length > 6 && !partes[6].trim().isEmpty()) {
                    String[] lugares = partes[6].split(",");
                    for (String lugar : lugares) {
                        if (!lugar.trim().isEmpty()) {
                            sala.getLugaresAcessiveis().add(lugar.trim());
                        }
                    }
                }

                return sala;
            }
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha + " - " + e.getMessage());
        }
        return null;
    }


    public List<Sala> getSalas() {
        return salas;
    }

    public void adicionarSala(Sala sala) {
        if (sala != null && !existeSala(sala.getNomeSala())) {
            salas.add(sala);
            gravarDados();
        }
    }

    public void removerSala(Sala sala) {
        if (salas.remove(sala)) {
            gravarDados();
        }
    }

    public void removerSala(String nomeSala) {
        salas.removeIf(sala -> sala.getNomeSala().equals(nomeSala));
        gravarDados();
    }

    public boolean existeSala(String nomeSala) {
        return salas.stream().anyMatch(sala -> sala.getNomeSala().equals(nomeSala));
    }

    public void atualizarSala(Sala salaAtualizada) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getNomeSala().equals(salaAtualizada.getNomeSala())) {
                salas.set(i, salaAtualizada);
                gravarDados();
                break;
            }
        }
    }

    public int getNumeroSalas() {
        return salas.size();
    }

    public void limparSalas() {
        salas.clear();
        gravarDados();
    }
}