package quiosque;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import restauracao.*;
import filmes.*;
import sessoes.*;
import vendas.*;
import faturas.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuioskPageTest {
    private static final Logger LOGGER = Logger.getLogger(QuioskPageTest.class.getName());
    
    @Mock
    private DadosRestauracao dadosRestauracao;
    @Mock
    private DadosFilmes dadosFilmes;
    @Mock
    private DadosSessoes dadosSessoes;
    @Mock
    private DadosVendas dadosVendas;
    @Mock
    private DadosFaturas dadosFaturas;

    static {
        LogManager.getLogManager().reset();
        ConsoleHandler handler = new ConsoleHandler() {
            @Override
            protected void setOutputStream(java.io.OutputStream out) throws SecurityException {
                super.setOutputStream(System.out);
            }
        };
        handler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("[%s] %s%n", 
                    record.getLevel().getLocalizedName(),
                    record.getMessage()
                );
            }
        });
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);
    }

    @BeforeAll
    void setup() {
        LOGGER.info("Initializing test suite");
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        LOGGER.info("Starting test: " + testInfo.getDisplayName());
        setupMockData();
    }

    private void setupMockData() {
        LOGGER.info("Setting up mock data");
        
        Sessao mockSessao = new Sessao("Filme Teste", "01/01/2024", "15:00", "Sala 1", true);
        List<Sessao> sessoes = new ArrayList<>();
        sessoes.add(mockSessao);
        when(dadosSessoes.getSessoes()).thenReturn(sessoes);
        LOGGER.info("Mocked session data: " + mockSessao);

        Filme mockFilme = new Filme("Ação", "Diretor", "120", "01/01/2024", "Filme Teste", true);
        when(dadosFilmes.getFilmePorTitulo("Filme Teste")).thenReturn(mockFilme);
        LOGGER.info("Mocked movie data: " + mockFilme);

        Produto mockProduto = new Produto("Pipoca", 5.0, 10, "Snack");
        List<Produto> produtos = new ArrayList<>();
        produtos.add(mockProduto);
        when(dadosRestauracao.getProdutos()).thenReturn(produtos);
        LOGGER.info("Mocked product data: " + mockProduto);

        List<String> produtosBundle = new ArrayList<>();
        produtosBundle.add("Pipoca");
        Bundle mockBundle = new Bundle(produtosBundle, 8.0, "Combo");
        List<Bundle> bundles = new ArrayList<>();
        bundles.add(mockBundle);
        when(dadosRestauracao.getBundles()).thenReturn(bundles);
        LOGGER.info("Mocked bundle data: " + mockBundle);

        Desconto mockDesconto = new Desconto("Estudante", 7.0);
        List<Desconto> descontos = new ArrayList<>();
        descontos.add(mockDesconto);
        when(dadosVendas.getDescontos()).thenReturn(descontos);
        when(dadosVendas.getPrecoBilhete()).thenReturn(10.0);
        LOGGER.info("Mocked discount data: " + mockDesconto);
        LOGGER.info("Mocked ticket price: 10.0€");
    }

    @Test
    @DisplayName("Teste de venda de bilhete normal sem produtos")
    void testVendaBilheteNormal(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        double precoBilhete = dadosVendas.getPrecoBilhete();
        LOGGER.info("Verifying ticket price. Expected: 10.0€, Actual: " + precoBilhete + "€");
        assertEquals(10.0, precoBilhete);
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de venda de bilhete com desconto")
    void testVendaBilheteComDesconto(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        List<Desconto> descontos = dadosVendas.getDescontos();
        LOGGER.info("Verifying discount availability. Available discounts: " + descontos.size());
        assertFalse(descontos.isEmpty());
        
        double valorDesconto = descontos.get(0).getValor();
        LOGGER.info("Verifying discount value. Expected: 7.0€, Actual: " + valorDesconto + "€");
        assertEquals(7.0, valorDesconto);
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de venda de bilhete com produto")
    void testVendaBilheteComProduto(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        List<Produto> produtos = dadosRestauracao.getProdutos();
        LOGGER.info("Verifying product availability. Available products: " + produtos.size());
        assertFalse(produtos.isEmpty());
        
        Produto produto = produtos.get(0);
        LOGGER.info("Verifying product price. Product: " + produto.getNome() + 
                   ", Expected price: 5.0€, Actual price: " + produto.getPreco() + "€");
        assertEquals(5.0, produto.getPreco());
        
        LOGGER.info("Verifying product stock. Product: " + produto.getNome() + 
                   ", Stock level: " + produto.getStock());
        assertTrue(produto.getStock() > 0);
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de venda de bilhete com bundle")
    void testVendaBilheteComBundle(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        List<Bundle> bundles = dadosRestauracao.getBundles();
        LOGGER.info("Verifying bundle availability. Available bundles: " + bundles.size());
        assertFalse(bundles.isEmpty());
        
        Bundle bundle = bundles.get(0);
        LOGGER.info("Verifying bundle price. Bundle products: " + bundle.getProdutosString() + 
                   ", Expected price: 8.0€, Actual price: " + bundle.getPreco() + "€");
        assertEquals(8.0, bundle.getPreco());
        
        LOGGER.info("Verifying bundle products. Products in bundle: " + bundle.getProdutosNomes());
        assertFalse(bundle.getProdutosNomes().isEmpty());
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de validação de NIF")
    void testValidacaoNIF(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        String nifInvalido = "12345";
        LOGGER.info("Testing invalid NIF: " + nifInvalido);
        assertFalse(isNIFValido(nifInvalido));
        
        String nifValido = "123456789";
        LOGGER.info("Testing valid NIF: " + nifValido);
        assertTrue(isNIFValido(nifValido));
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de validação de idade")
    void testValidacaoIdade(TestInfo testInfo) {
        LOGGER.info("Executing test: " + testInfo.getDisplayName());
        
        String idadeInvalida = "-5";
        LOGGER.info("Testing invalid age (negative): " + idadeInvalida);
        assertFalse(isIdadeValida(idadeInvalida));
        
        String idadeInvalida2 = "121";
        LOGGER.info("Testing invalid age (too high): " + idadeInvalida2);
        assertFalse(isIdadeValida(idadeInvalida2));
        
        String idadeValida = "25";
        LOGGER.info("Testing valid age: " + idadeValida);
        assertTrue(isIdadeValida(idadeValida));
        
        LOGGER.info("Test completed successfully");
    }

    private boolean isNIFValido(String nif) {
        boolean isValid = nif != null && nif.matches("\\d{9}");
        LOGGER.info("Validating NIF: " + nif + " -> " + (isValid ? "valid" : "invalid"));
        return isValid;
    }

    private boolean isIdadeValida(String idade) {
        try {
            int idadeNum = Integer.parseInt(idade);
            boolean isValid = idadeNum >= 0 && idadeNum <= 120;
            LOGGER.info("Validating age: " + idade + " -> " + (isValid ? "valid" : "invalid"));
            return isValid;
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid age format: " + idade);
            return false;
        }
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        LOGGER.info("Finishing test: " + testInfo.getDisplayName());
    }

    @AfterAll
    void cleanup() {
        LOGGER.info("Test suite completed");
    }
} 