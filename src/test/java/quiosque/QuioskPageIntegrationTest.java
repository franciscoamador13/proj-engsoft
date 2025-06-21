package quiosque;

import org.junit.jupiter.api.*;
import faturas.*;
import vendas.*;
import restauracao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuioskPageIntegrationTest {
    private static final Logger LOGGER = Logger.getLogger(QuioskPageIntegrationTest.class.getName());
    private DadosFaturas dadosFaturas;
    private DadosVendas dadosVendas;
    private DadosRestauracao dadosRestauracao;

    static {
        // Configure logger to use a simpler format
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
        LOGGER.info("Initializing integration test suite");
        
        // Initialize data managers
        dadosFaturas = DadosFaturas.getInstance();
        dadosVendas = DadosVendas.getInstance();
        dadosRestauracao = DadosRestauracao.getInstance();
        
        // Clear any existing data
        dadosFaturas.limparFaturas();
        dadosVendas.limparDescontos();
        dadosRestauracao.limparProdutos();
        dadosRestauracao.limparBundles();
        
        LOGGER.info("Test suite initialized and data cleared");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        LOGGER.info(() -> "Starting integration test: " + testInfo.getDisplayName());
        
        // Clear any existing data
        dadosFaturas.limparFaturas();
        dadosVendas.limparDescontos();
        dadosRestauracao.limparProdutos();
        dadosRestauracao.limparBundles();
        
        // Setup test data
        setupTestData();
        
        LOGGER.info("Test setup complete - Data managers initialized and test data setup");
    }

    private void setupTestData() {
        LOGGER.info("Setting up test data");
        
        // Setup produtos
        Produto pipoca = new Produto("Pipoca", 5.0, 10, "Snack");
        Produto bebida = new Produto("Coca-Cola", 3.0, 10, "Bebida");
        dadosRestauracao.adicionarProduto(pipoca);
        dadosRestauracao.adicionarProduto(bebida);
        LOGGER.info("Added test products: Pipoca (5.0€), Coca-Cola (3.0€)");
        
        // Setup bundle
        List<String> produtosBundle = new ArrayList<>();
        produtosBundle.add("Pipoca");
        produtosBundle.add("Coca-Cola");
        Bundle bundle = new Bundle(produtosBundle, 7.0, "Combo");
        dadosRestauracao.adicionarBundle(bundle);
        LOGGER.info("Added test bundle: Combo Pipoca + Bebida (7.0€)");
        
        // Setup desconto
        Desconto desconto = new Desconto("Estudante", 7.0);
        dadosVendas.adicionarDesconto(desconto);
        dadosVendas.setPrecoBilhete(10.0);
        LOGGER.info("Added test discount: Estudante (7.0€)");
        LOGGER.info("Set ticket price: 10.0€");
        
        // Verify data was properly set up
        assertFalse(dadosRestauracao.getProdutos().isEmpty(), "Products should be initialized");
        assertFalse(dadosRestauracao.getBundles().isEmpty(), "Bundles should be initialized");
        assertFalse(dadosVendas.getDescontos().isEmpty(), "Discounts should be initialized");
        assertEquals(10.0, dadosVendas.getPrecoBilhete(), "Ticket price should be set to 10.0€");
    }

    @Test
    @DisplayName("Teste de integração - Criar fatura com bilhete normal")
    void testCriarFaturaBilheteNormal(TestInfo testInfo) {
        LOGGER.info(() -> "Executing test: " + testInfo.getDisplayName());
        
        // Arrange
        String sessao = "Filme Teste - 01/01/2024 15:00 (Sala 1)";
        String sala = "Sala 1";
        LOGGER.info("Test data - Session: " + sessao + ", Room: " + sala);
        
        // Act
        LOGGER.info("Creating new fatura with normal ticket");
        Fatura fatura = dadosFaturas.criarNovaFatura(null, null, sessao, sala);
        double precoBilhete = dadosVendas.getPrecoBilhete();
        LOGGER.info("Adding ticket line - Price: " + precoBilhete + "€");
        fatura.adicionarLinhaBilhete(null, precoBilhete);
        dadosFaturas.adicionarFatura(fatura);
        
        // Assert
        LOGGER.info("Verifying fatura creation - Expected total faturas: 1, Actual: " + dadosFaturas.getNumeroTotalFaturas());
        assertEquals(1, dadosFaturas.getNumeroTotalFaturas());
        
        LOGGER.info("Verifying fatura total - Expected: " + precoBilhete + "€, Actual: " + fatura.getTotal() + "€");
        assertEquals(precoBilhete, fatura.getTotal());
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de integração - Criar fatura com bilhete e desconto")
    void testCriarFaturaBilheteComDesconto(TestInfo testInfo) {
        LOGGER.info(() -> "Executing test: " + testInfo.getDisplayName());
        
        // Arrange
        String sessao = "Filme Teste - 01/01/2024 15:00 (Sala 1)";
        String sala = "Sala 1";
        Desconto desconto = dadosVendas.getDescontos().get(0);
        LOGGER.info("Test data - Session: " + sessao + ", Room: " + sala + 
                   ", Discount type: " + desconto.getCondicao() + ", Value: " + desconto.getValor() + "€");
        
        // Act
        LOGGER.info("Creating new fatura with discounted ticket");
        Fatura fatura = dadosFaturas.criarNovaFatura(null, null, sessao, sala);
        LOGGER.info("Adding ticket line with discount - Value: " + desconto.getValor() + "€");
        fatura.adicionarLinhaBilhete(desconto.getCondicao(), desconto.getValor());
        dadosFaturas.adicionarFatura(fatura);
        
        // Assert
        LOGGER.info("Verifying fatura creation - Expected total faturas: 1, Actual: " + dadosFaturas.getNumeroTotalFaturas());
        assertEquals(1, dadosFaturas.getNumeroTotalFaturas());
        
        LOGGER.info("Verifying fatura total - Expected: " + desconto.getValor() + "€, Actual: " + fatura.getTotal() + "€");
        assertEquals(desconto.getValor(), fatura.getTotal());
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de integração - Criar fatura com bilhete e produto")
    void testCriarFaturaBilheteComProduto(TestInfo testInfo) {
        LOGGER.info(() -> "Executing test: " + testInfo.getDisplayName());
        
        // Arrange
        String sessao = "Filme Teste - 01/01/2024 15:00 (Sala 1)";
        String sala = "Sala 1";
        Produto produto = dadosRestauracao.getProdutos().get(0);
        double precoBilhete = dadosVendas.getPrecoBilhete();
        double totalEsperado = precoBilhete + produto.getPreco();
        
        LOGGER.info("Test data - Session: " + sessao + ", Room: " + sala);
        LOGGER.info("Product details - Name: " + produto.getNome() + ", Price: " + produto.getPreco() + "€");
        LOGGER.info("Ticket price: " + precoBilhete + "€");
        LOGGER.info("Expected total: " + totalEsperado + "€");
        
        // Act
        LOGGER.info("Creating new fatura with ticket and product");
        Fatura fatura = dadosFaturas.criarNovaFatura(null, null, sessao, sala);
        LOGGER.info("Adding ticket line - Price: " + precoBilhete + "€");
        fatura.adicionarLinhaBilhete(null, precoBilhete);
        LOGGER.info("Adding product line - " + produto.getNome() + ": " + produto.getPreco() + "€");
        fatura.adicionarLinha(produto.getNome(), produto.getPreco(), 1);
        dadosFaturas.adicionarFatura(fatura);
        
        // Assert
        LOGGER.info("Verifying fatura creation - Expected total faturas: 1, Actual: " + dadosFaturas.getNumeroTotalFaturas());
        assertEquals(1, dadosFaturas.getNumeroTotalFaturas());
        
        LOGGER.info("Verifying fatura total - Expected: " + totalEsperado + "€, Actual: " + fatura.getTotal() + "€");
        assertEquals(totalEsperado, fatura.getTotal());
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de integração - Criar fatura com bilhete e bundle")
    void testCriarFaturaBilheteComBundle(TestInfo testInfo) {
        LOGGER.info(() -> "Executing test: " + testInfo.getDisplayName());
        
        // Verify bundles are available
        List<Bundle> bundles = dadosRestauracao.getBundles();
        assertFalse(bundles.isEmpty(), "Bundles should be available for testing");
        
        // Arrange
        String sessao = "Filme Teste - 01/01/2024 15:00 (Sala 1)";
        String sala = "Sala 1";
        Bundle bundle = bundles.get(0);
        double precoBilhete = dadosVendas.getPrecoBilhete();
        double totalEsperado = precoBilhete + bundle.getPreco();
        
        LOGGER.info("Test data - Session: " + sessao + ", Room: " + sala);
        LOGGER.info("Bundle details - Products: " + bundle.getProdutosString() + ", Price: " + bundle.getPreco() + "€");
        LOGGER.info("Ticket price: " + precoBilhete + "€");
        LOGGER.info("Expected total: " + totalEsperado + "€");
        
        // Act
        LOGGER.info("Creating new fatura with ticket and bundle");
        Fatura fatura = dadosFaturas.criarNovaFatura(null, null, sessao, sala);
        LOGGER.info("Adding ticket line - Price: " + precoBilhete + "€");
        fatura.adicionarLinhaBilhete(null, precoBilhete);
        LOGGER.info("Adding bundle line - " + bundle.getProdutosString() + ": " + bundle.getPreco() + "€");
        fatura.adicionarLinha("Bundle: " + bundle.getProdutosString(), bundle.getPreco(), 1);
        dadosFaturas.adicionarFatura(fatura);
        
        // Assert
        LOGGER.info("Verifying fatura creation - Expected total faturas: 1, Actual: " + dadosFaturas.getNumeroTotalFaturas());
        assertEquals(1, dadosFaturas.getNumeroTotalFaturas());
        
        LOGGER.info("Verifying fatura total - Expected: " + totalEsperado + "€, Actual: " + fatura.getTotal() + "€");
        assertEquals(totalEsperado, fatura.getTotal());
        
        LOGGER.info("Test completed successfully");
    }

    @Test
    @DisplayName("Teste de integração - Criar fatura com NIF e idade")
    void testCriarFaturaComNIFEIdade(TestInfo testInfo) {
        LOGGER.info(() -> "Executing test: " + testInfo.getDisplayName());
        
        // Arrange
        String sessao = "Filme Teste - 01/01/2024 15:00 (Sala 1)";
        String sala = "Sala 1";
        String nif = "123456789";
        String idade = "25";
        double precoBilhete = dadosVendas.getPrecoBilhete();
        
        LOGGER.info("Test data - Session: " + sessao + ", Room: " + sala);
        LOGGER.info("Customer details - NIF: " + nif + ", Age: " + idade);
        LOGGER.info("Ticket price: " + precoBilhete + "€");
        
        // Act
        LOGGER.info("Creating new fatura with customer information");
        Fatura fatura = dadosFaturas.criarNovaFatura(nif, idade, sessao, sala);
        LOGGER.info("Adding ticket line - Price: " + precoBilhete + "€");
        fatura.adicionarLinhaBilhete(null, precoBilhete);
        dadosFaturas.adicionarFatura(fatura);
        
        // Assert
        LOGGER.info("Verifying fatura creation - Expected total faturas: 1, Actual: " + dadosFaturas.getNumeroTotalFaturas());
        assertEquals(1, dadosFaturas.getNumeroTotalFaturas());
        
        LOGGER.info("Verifying customer NIF - Expected: " + nif + ", Actual: " + fatura.getNif());
        assertEquals(nif, fatura.getNif());
        
        List<Fatura> faturasNIF = dadosFaturas.getFaturasPorNif(nif);
        LOGGER.info("Verifying faturas by NIF - Expected count: 1, Actual: " + faturasNIF.size());
        assertEquals(1, faturasNIF.size());
        
        LOGGER.info("Test completed successfully");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        LOGGER.info(() -> "Finishing test: " + testInfo.getDisplayName());
        // Clean up after each test
        dadosFaturas.limparFaturas();
        LOGGER.info("Test cleanup complete - All faturas cleared");
    }

    @AfterAll
    void cleanup() {
        LOGGER.info("Integration test suite completed");
        // Clean up all test data
        dadosFaturas.limparFaturas();
        dadosVendas.limparDescontos();
        dadosRestauracao.limparProdutos();
        dadosRestauracao.limparBundles();
    }
} 