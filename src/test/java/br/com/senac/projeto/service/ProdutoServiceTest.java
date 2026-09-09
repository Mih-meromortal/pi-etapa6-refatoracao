package br.com.senac.projeto.service;

import br.com.senac.projeto.factory.ProdutoFactory;
import br.com.senac.projeto.model.Produto;
import br.com.senac.projeto.repository.ProdutoRepository;
import br.com.senac.projeto.repository.ProdutoRepositoryMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoServiceTest {

    private ProdutoRepository repository;
    private ProdutoService service;

    @BeforeEach
    public void setUp() {
        repository = new ProdutoRepositoryMemoria();
        service = new ProdutoService(repository);
    }

    @Test
    public void testAplicarDescontoValido() {
        Produto p = ProdutoFactory.criar(1, "Notebook Corporativo", 3000.0, 10);
        service.cadastrarProduto(p);

        double precoAtualizado = service.aplicarDesconto(1, 10.0);

        assertEquals(2700.0, precoAtualizado, 0.001);
        assertEquals(2700.0, p.getPreco(), 0.001);
    }

    @Test
    public void testAplicarDescontoAcimaDoLimiteDeveLancarExcecao() {
        Produto p = ProdutoFactory.criar(1, "Notebook Corporativo", 3000.0, 10);
        service.cadastrarProduto(p);

        assertThrows(IllegalArgumentException.class, () -> {
            service.aplicarDesconto(1, 60.0);
        });
    }

    @Test
    public void testDarBaixaEstoqueComSucesso() {
        Produto p = ProdutoFactory.criar(2, "Teclado Mecânico", 250.0, 15);
        service.cadastrarProduto(p);

        service.darBaixaEstoque(2, 5);

        assertEquals(10, p.getQuantidadeEstoque());
    }

    @Test
    public void testDarBaixaEstoqueInsuficienteDeveLancarExcecao() {
        Produto p = ProdutoFactory.criar(2, "Teclado Mecânico", 250.0, 5);
        service.cadastrarProduto(p);

        assertThrows(IllegalStateException.class, () -> {
            service.darBaixaEstoque(2, 10);
        });
    }
}