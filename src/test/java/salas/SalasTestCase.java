package salas;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalasTestCase {

    @Test
    public void testValidarCriarSala() {
        var sala = new Sala("Sala Premium", "IMAX", "90–105 dB", 15, 20, true);

        assert sala.getNomeSala().equals("Sala Premium");
        assert sala.getNivelProjecao().equals("IMAX");
        assert sala.getNivelSom().equals("90–105 dB");
        assert sala.getLinhas() == 15;
        assert sala.getColunas() == 20;
        assert sala.isAcessivelCadeirantes();
        assert sala.getLugaresAcessiveis().isEmpty();
    }

    @Test
    public void testCriarSalaComCadeirassacessiveis() {
        var sala = new Sala("Sala VIP", "3D", "75–80 dB", 10, 12, true);

        sala.getLugaresAcessiveis().addAll(Arrays.asList("A1", "A2", "B1", "B2"));

        assert sala.isAcessivelCadeirantes();
        assert sala.getLugaresAcessiveis().size() == 4;
        assert sala.getLugaresAcessiveis().contains("A1");
        assert sala.getLugaresAcessiveis().contains("B2");

        String expected = "Sala VIP - 3D (10x12) - 75–80 dB [Lugares Acessíveis: A1, A2, B1, B2]";
        assert sala.toString().equals(expected);
    }

    @Test
    public void testvalidarEditarSala() {
        var sala = new Sala("Sala Standard", "2D", "60 dB", 8, 10, false);

        sala.setNomeSala("Sala Atualizada");
        sala.setNivelProjecao("3D");
        sala.setNivelSom("70–75 dB");
        sala.setLinhas(12);
        sala.setColunas(15);
        sala.setAcessivelCadeirantes(true);

        assert sala.getNomeSala().equals("Sala Atualizada");
        assert sala.getNivelProjecao().equals("3D");
        assert sala.getNivelSom().equals("70–75 dB");
        assert sala.getLinhas() == 12;
        assert sala.getColunas() == 15;
        assert sala.isAcessivelCadeirantes();
    }

    @Test
    public void testSalaComAcessibilidade() {
        var sala = new Sala("Sala Básica", "2D", "60 dB", 6, 8, false);

        assert !sala.isAcessivelCadeirantes();
        assert sala.getLugaresAcessiveis().isEmpty();


        String expected = "Sala Básica - 2D (6x8) - 60 dB";
        assert sala.toString().equals(expected);


        sala.getLugaresAcessiveis().add("C3");
        assert sala.toString().equals(expected);
    }

    @Test
    public void testSalaAtivacaoAcessibilidade() {
        var sala = new Sala("Sala Flexível", "3D", "75–80 dB", 8, 10, true);

        assert sala.isAcessivelCadeirantes();

        sala.getLugaresAcessiveis().addAll(Arrays.asList("A1", "A2", "H9", "H10"));
        assert sala.getLugaresAcessiveis().size() == 4;

        String toStringComAcessibilidade = sala.toString();
        assert toStringComAcessibilidade.contains("Lugares Acessíveis");
        assert toStringComAcessibilidade.contains("A1, A2, H9, H10");


        sala.setAcessivelCadeirantes(false);
        assert !sala.isAcessivelCadeirantes();


        String toStringSemAcessibilidade = sala.toString();
        assert !toStringSemAcessibilidade.contains("Lugares Acessíveis");
        assert !toStringSemAcessibilidade.contains("A1, A2, H9, H10");


        assert sala.getLugaresAcessiveis().size() == 4;


        sala.setAcessivelCadeirantes(true);
        String toStringReativado = sala.toString();
        assert toStringReativado.contains("Lugares Acessíveis");
        assert toStringReativado.contains("A1, A2, H9, H10");
    }
}