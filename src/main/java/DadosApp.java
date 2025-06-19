import java.util.ArrayList;
import java.util.List;


public class DadosApp {
    private static DadosApp instance = null;

    private List<Filme> filmes = new ArrayList<>();
    private List<Sessao> sessoes = new ArrayList<>();

    private DadosApp() {
        // Inicializa com alguns filmes e sessões
        filmes.add(new Filme("Interstellar", "Sci-fi","Christopher Nolan",150L,"24-10-2014" ));
        filmes.add(new Filme("Barbie", "Comédia","Greta Gerwig",114L,"20-07-2023" ));
        filmes.add(new Filme("O Senhor dos Anéis: A Sociedade do Anel", "Fantasia","Peter Jackson",178L,"19-12-2001" ));

        sessoes.add(new Sessao("Interstellar", true, 1630L, "21-10-2025", 1L));
        sessoes.add(new Sessao("Barbie", true, 1500L, "22-10-2025", 2L));
        sessoes.add(new Sessao("O Senhor dos Anéis: A Sociedade do Anel", true, 1800L, "23-10-2025", 3L));
    }

    public static DadosApp getInstance() {
        if (instance == null) {
            instance = new DadosApp();
        }
        return instance;
    }

    private static void carregarDados() {
        // Aqui você pode carregar os dados de um arquivo ou banco de dados
        // Por enquanto, vamos usar os dados já inicializados no construtor
    }

    private static void gravarDados() {
        // Aqui você pode salvar os dados em um arquivo ou banco de dados
        // Por enquanto, não faremos nada
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }
}