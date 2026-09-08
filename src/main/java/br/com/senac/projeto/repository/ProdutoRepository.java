package br.com.senac.projeto.repository;

import br.com.senac.projeto.model.Produto;
import java.util.List;

public interface ProdutoRepository {
    void salvar(Produto produto);
    Produto buscarPorId(int id);
    List<Produto> listarTodos();
    void atualizar(Produto produto);
    void excluir(int id);
}