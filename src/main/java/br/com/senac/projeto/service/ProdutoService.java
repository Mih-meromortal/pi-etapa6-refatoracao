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
            throw new IllegalArgumentException("Produto inválido.");
        }
        if (repository.buscarPorId(produto.getId()) != null) {
            throw new IllegalStateException("Produto com ID " + produto.getId() + " já cadastrado.");
        }
        repository.salvar(produto);
    }

    public double aplicarDesconto(int id, double percentual) {
        if (percentual <= 0 || percentual > 50) {
            throw new IllegalArgumentException("Desconto deve estar entre 1% e 50%.");
        }
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto inexistente para cálculo de desconto.");
        }
        double novoPreco = produto.getPreco() - (produto.getPreco() * (percentual / 100));
        produto.setPreco(novoPreco);
        repository.atualizar(produto);
        return novoPreco;
    }

    public void darBaixaEstoque(int id, int quantidade) {
        Produto produto = repository.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto inexistente.");
        }
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalStateException("Estoque insuficiente.");
        }
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        repository.atualizar(produto);
    }

    public List<Produto> obterTodos() {
        return repository.listarTodos();
    }
}