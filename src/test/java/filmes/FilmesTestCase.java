package filmes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmesTestCase {

    @Test
    public void testCreateFilme_valid(){
        var filme = new Filme("Ação", "John Doe", "2h", "2023-10-01", "Aventura no Espaço");

        assert filme.getTipo().equals("Ação");
        assert filme.getRealizacao().equals("John Doe");
        assert filme.getDuracao().equals("2h");
        assert filme.getDataLancamento().equals("2023-10-01");
        assert filme.getTitulo().equals("Aventura no Espaço");
        assert filme.isAtivo(); // By default, new movies are active
    }
    @Test
    public void testCreateFilme_invalid(){
        // Exemplo: título nulo ou vazio
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Filme("Ação", "John Doe", "2h", "2023-10-01", null);
        });

        assert exception.getMessage().contains("Título não pode ser nulo");
    }


    @Test
    public void testUpdateFilme_valid(){
        var filme = new Filme("Ação", "John Doe", "2h", "2023-10-01", "Aventura no Espaço");
        filme.setTitulo("Aventura no Espaço 2");
        filme.setAtivo(false);

        assert filme.getTitulo().equals("Aventura no Espaço 2");
        assert !filme.isAtivo(); // Now the movie is inactive
    }

    @Test
    public void testUpdateFilme_invalid() {
        var filme = new Filme("Ação", "John Doe", "2h", "2023-10-01", "Aventura no Espaço");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filme.setTitulo(null); // Tentativa inválida
        });

        assert exception.getMessage().contains("Título não pode ser nulo");
    }

    @Test
    public void testAddFilmesWithSameTitle() {
        var filme1 = new Filme("Crime", "Rick Rosenthal", "2h", "1983-03-25", "Bad Boys");
        var filme2 = new Filme("Ação", "Michael Bay", "2h", "1995-07-07", "Bad Boys");

        assert filme1.getTitulo().equals(filme2.getTitulo());

        assert !filme1.getTipo().equals(filme2.getTipo());
        assert !filme1.getRealizacao().equals(filme2.getRealizacao());
        assert !filme1.getDataLancamento().equals(filme2.getDataLancamento());

    }
}
