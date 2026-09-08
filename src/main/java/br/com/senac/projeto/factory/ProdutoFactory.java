package br.com.senac.projeto.factory;

import br.com.senac.projeto.model.Produto;

public class ProdutoFactory {
    public static Produto criar(int id, String nome, double preco, int estoque) {
        if (preco < 0 || estoque < 0) {
            throw new IllegalArgumentException("Preço e estoque não podem ser negativos.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        return new Produto(id, nome.trim(), preco, estoque);
    }
}