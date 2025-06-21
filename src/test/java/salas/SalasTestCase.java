package salas;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalasTestCase {

    @Test
    public void testCreateSala_valid() {
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
    public void testCreateSala_withAccessibleSeats() {
        var sala = new Sala("Sala VIP", "3D", "75–80 dB", 10, 12, true);

        // Adicionar lugares acessíveis
        sala.getLugaresAcessiveis().addAll(Arrays.asList("A1", "A2", "B1", "B2"));

        assert sala.isAcessivelCadeirantes();
        assert sala.getLugaresAcessiveis().size() == 4;
        assert sala.getLugaresAcessiveis().contains("A1");
        assert sala.getLugaresAcessiveis().contains("B2");

        // Testar toString com lugares acessíveis
        String expected = "Sala VIP - 3D (10x12) - 75–80 dB [Lugares Acessíveis: A1, A2, B1, B2]";
        assert sala.toString().equals(expected);
    }

    @Test
    public void testUpdateSala_valid() {
        var sala = new Sala("Sala Standard", "2D", "60 dB", 8, 10, false);

        // Atualizar propriedades
        sala.setNomeSala("Sala Renovada");
        sala.setNivelProjecao("3D");
        sala.setNivelSom("70–75 dB");
        sala.setLinhas(12);
        sala.setColunas(15);
        sala.setAcessivelCadeirantes(true);

        assert sala.getNomeSala().equals("Sala Renovada");
        assert sala.getNivelProjecao().equals("3D");
        assert sala.getNivelSom().equals("70–75 dB");
        assert sala.getLinhas() == 12;
        assert sala.getColunas() == 15;
        assert sala.isAcessivelCadeirantes();
    }

    @Test
    public void testSalaWithoutAccessibility() {
        var sala = new Sala("Sala Básica", "2D", "60 dB", 6, 8, false);

        assert !sala.isAcessivelCadeirantes();
        assert sala.getLugaresAcessiveis().isEmpty();

        // Testar toString sem acessibilidade
        String expected = "Sala Básica - 2D (6x8) - 60 dB";
        assert sala.toString().equals(expected);

        // Mesmo adicionando lugares acessíveis, não devem aparecer no toString se acessivelCadeirantes é false
        sala.getLugaresAcessiveis().add("C3");
        assert sala.toString().equals(expected); // Deve manter o mesmo resultado
    }

    @Test
    public void testSalasWithSameName_differentSpecs() {
        var sala1 = new Sala("Cinema 1", "2D", "60 dB", 10, 12, false);
        var sala2 = new Sala("Cinema 1", "IMAX", "115 dB", 20, 25, true);

        // Mesmo nome, mas especificações diferentes
        assert sala1.getNomeSala().equals(sala2.getNomeSala());

        // Verificar que as outras propriedades são diferentes
        assert !sala1.getNivelProjecao().equals(sala2.getNivelProjecao());
        assert !sala1.getNivelSom().equals(sala2.getNivelSom());
        assert sala1.getLinhas() != sala2.getLinhas();
        assert sala1.getColunas() != sala2.getColunas();
        assert sala1.isAcessivelCadeirantes() != sala2.isAcessivelCadeirantes();

        // Verificar toString diferentes
        assert !sala1.toString().equals(sala2.toString());
    }
}