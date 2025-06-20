package restauracao;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DadosRestauracao {
    private static DadosRestauracao instance = null;
    private static final String FICHEIRO_DADOS = "restauracao.txt";
    private static final String FICHEIRO_BUNDLES = "bundles.txt";
    private List<Produto> produtos;
    private List<Bundle> bundles;
    
    private DadosRestauracao() {
        produtos = new ArrayList<>();
        bundles = new ArrayList<>();
        carregarDados();
    }
    
    public static DadosRestauracao getInstance() {
        if (instance == null) {
            instance = new DadosRestauracao();
        }
        return instance;
    }
    
    private void carregarDados() {
        carregarProdutos();
        carregarBundles();
    }

    private void carregarProdutos() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_DADOS))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_DADOS))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            Produto produto = analisarLinha(linha);
                            if (produto != null) {
                                produtos.add(produto);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void carregarBundles() {
        try {
            if (Files.exists(Paths.get(FICHEIRO_BUNDLES))) {
                try (BufferedReader reader = new BufferedReader(new FileReader(FICHEIRO_BUNDLES))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (!linha.trim().isEmpty()) {
                            Bundle bundle = analisarLinhaBundle(linha);
                            if (bundle != null) {
                                bundles.add(bundle);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar bundles: " + e.getMessage());
        }
    }

    public void gravarDados() {
        gravarProdutos();
        gravarBundles();
    }

    private void gravarProdutos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_DADOS))) {
            for (Produto produto : produtos) {
                writer.println(produtoParaString(produto));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar produtos: " + e.getMessage());
        }
    }

    private void gravarBundles() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHEIRO_BUNDLES))) {
            for (Bundle bundle : bundles) {
                writer.println(bundleParaString(bundle));
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar bundles: " + e.getMessage());
        }
    }

    private String produtoParaString(Produto produto) {
        return String.format("%s|%s|%d|%s",
            produto.getNome(),
            String.format("%.2f", produto.getPreco()).replace(".", ","),
            produto.getStock(),
            produto.getTipo()
        );
    }

    private String bundleParaString(Bundle bundle) {
        return String.format("%s|%s|%s",
            String.join(",", bundle.getProdutosNomes()),
            String.format("%.2f", bundle.getPreco()).replace(".", ","),
            bundle.getTipo()
        );
    }

    private Produto analisarLinha(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 4) {
                String nome = partes[0];
                double preco = Double.parseDouble(partes[1].replace(",", "."));
                int stock = Integer.parseInt(partes[2]);
                String tipo = partes[3];
                return new Produto(nome, preco, stock, tipo);
            }
        } catch (Exception e) {
            System.err.println("Erro ao analisar linha de produto: " + linha + " - " + e.getMessage());
        }
        return null;
    }

    private Bundle analisarLinhaBundle(String linha) {
        try {
            String[] partes = linha.split("\\|");
            if (partes.length >= 3) {
                List<String> produtos = Arrays.asList(partes[0].split(","));
                double preco = Double.parseDouble(partes[1].replace(",", "."));
                String tipo = partes[2];
                return new Bundle(produtos, preco, tipo);
            }
        } catch (Exception e) {
            System.err.println("Erro ao analisar linha de bundle: " + linha + " - " + e.getMessage());
        }
        return null;
    }
    
    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public List<Bundle> getBundles() {
        return new ArrayList<>(bundles);
    }

    public void adicionarProduto(Produto produto) {
        if (produto != null && !existeProduto(produto.getNome())) {
            produtos.add(produto);
            gravarDados();
        }
    }

    public void adicionarBundle(Bundle bundle) {
        if (bundle != null) {
            bundles.add(bundle);
            gravarDados();
        }
    }
    
    public void removerProduto(Produto produto) {
        if (produtos.remove(produto)) {
            gravarDados();
        }
    }

    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equals(nome));
        gravarDados();
    }

    public void removerBundle(Bundle bundle) {
        if (bundles.remove(bundle)) {
            gravarDados();
        }
    }

    public void removerBundle(String produtosString) {
        bundles.removeIf(b -> b.getProdutosString().equals(produtosString));
        gravarDados();
    }

    public boolean existeProduto(String nome) {
        return produtos.stream().anyMatch(p -> p.getNome().equals(nome));
    }

    public void atualizarProduto(Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getNome().equals(produtoAtualizado.getNome())) {
                produtos.set(i, produtoAtualizado);
                gravarDados();
                break;
            }
        }
    }

    public void atualizarBundle(Bundle bundleAtualizado) {
        for (int i = 0; i < bundles.size(); i++) {
            if (bundles.get(i).getProdutosString().equals(bundleAtualizado.getProdutosString())) {
                bundles.set(i, bundleAtualizado);
                gravarDados();
                break;
            }
        }
    }

    public int getNumeroProdutos() {
        return produtos.size();
    }

    public int getNumeroBundles() {
        return bundles.size();
    }

    public void limparProdutos() {
        produtos.clear();
        gravarDados();
    }

    public void limparBundles() {
        bundles.clear();
        gravarDados();
    }

    public Produto getProdutoPorNome(String nome) {
        return produtos.stream()
                .filter(p -> p.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }
} 