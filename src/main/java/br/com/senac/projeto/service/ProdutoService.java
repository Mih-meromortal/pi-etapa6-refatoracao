package br.com.senac.projeto.service;

import br.com.senac.projeto.model.Produto;
import br.com.senac.projeto.repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void cadastrarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        repository.salvar(produto);
    }

    public void salvar(Produto produto) {
        cadastrarProduto(produto);
    }

    public List<Produto> obterTodos() {
        return repository.listarTodos();
    }

    public List<Produto> listarTodos() {
        return obterTodos();
    }

    public Produto buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public void excluir(int id) {
        repository.excluir(id);
    }

    public double aplicarDesconto(int id, double percentual) {
        if (percentual <= 0) {
            throw new IllegalArgumentException("O desconto deve ser maior que zero.");
        }

        // Converte se vier em formato percentual (ex: 60) ou decimal (ex: 0.60)
        double taxa = (percentual > 1.0) ? (percentual / 100.0) : percentual;

        // Regra de negócio: teto máximo de 50% de desconto
        if (taxa > 0.50) {
            throw new IllegalArgumentException("O percentual de desconto não pode ser superior a 50%.");
        }

        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }

        double novoPreco = produto.getPreco() * (1.0 - taxa);
        produto.setPreco(novoPreco);
        repository.salvar(produto);
        return novoPreco;
    }

    public void darBaixaEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de baixa deve ser maior que zero.");
        }
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalStateException("Saldo insuficiente em estoque.");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        repository.salvar(produto);
    }

    public void adicionarEstoque(int id, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade de entrada deve ser maior que zero.");
        }
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        repository.salvar(produto);
    }
}