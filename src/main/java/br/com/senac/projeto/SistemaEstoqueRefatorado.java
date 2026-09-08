package br.com.senac.projeto;

import br.com.senac.projeto.factory.ProdutoFactory;
import br.com.senac.projeto.model.Produto;
import br.com.senac.projeto.repository.ProdutoRepository;
import br.com.senac.projeto.repository.ProdutoRepositoryMemoria;
import br.com.senac.projeto.service.ProdutoService;

public class SistemaEstoqueRefatorado {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   VALIDAÇÃO DA ARQUITETURA REFATORADA (ETAPA 6)   ");
        System.out.println("==================================================");

        ProdutoRepository repository = new ProdutoRepositoryMemoria();
        ProdutoService service = new ProdutoService(repository);

        System.out.println("\n[TESTE 1] Criação via Factory e Inserção no Service:");
        Produto p1 = ProdutoFactory.criar(1, "Notebook Corporativo", 3500.00, 10);
        Produto p2 = ProdutoFactory.criar(2, "Teclado Mecânico", 250.00, 20);
        service.cadastrarProduto(p1);
        service.cadastrarProduto(p2);
        service.obterTodos().forEach(System.out::println);

        System.out.println("\n[TESTE 2] Regra de Negócio (Desconto de 10% no Produto 1):");
        double precoAtualizado = service.aplicarDesconto(1, 10.0);
        System.out.println("Novo preço do produto ID 1: R$ " + String.format("%.2f", precoAtualizado));

        System.out.println("\n[TESTE 3] Atualização de Estoque (Baixa de 3 unidades do Produto 2):");
        service.darBaixaEstoque(2, 3);
        System.out.println("Status atualizado: " + repository.buscarPorId(2));

        System.out.println("\n[TESTE 4] Tratamento de Exceção (Tentativa de baixa com estoque insuficiente):");
        try {
            service.darBaixaEstoque(2, 50);
        } catch (Exception ex) {
            System.out.println("Exceção capturada corretamente: " + ex.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println("   STATUS: ARQUITETURA PRONTA PARA CAMADA WEB     ");
        System.out.println("==================================================");
    }
}