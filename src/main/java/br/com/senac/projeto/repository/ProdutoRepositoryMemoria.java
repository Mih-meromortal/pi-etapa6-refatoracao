package br.com.senac.projeto.repository;

import br.com.senac.projeto.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryMemoria implements ProdutoRepository {
    private final List<Produto> bancoDados = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        bancoDados.add(produto);
    }

    @Override
    public Produto buscarPorId(int id) {
        for (Produto p : bancoDados) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(bancoDados);
    }

    @Override
    public void atualizar(Produto produto) {
        for (int i = 0; i < bancoDados.size(); i++) {
            if (bancoDados.get(i).getId() == produto.getId()) {
                bancoDados.set(i, produto);
                return;
            }
        }
    }

    @Override
    public void excluir(int id) {
        bancoDados.removeIf(p -> p.getId() == id);
    }
}