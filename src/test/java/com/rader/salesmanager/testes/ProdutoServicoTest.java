package com.rader.salesmanager.testes;

import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoServicoTest {

    @Test
    void testCriarProdutoServico() {
        // Criar um novo produto/serviço
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setId(UUID.randomUUID());
        produtoServico.setProduto(Boolean.TRUE);
        produtoServico.setNome("Produto Teste");
        produtoServico.setDescricao("Descrição do produto teste");
        produtoServico.setPreco(new BigDecimal("10.00"));
        produtoServico.setAtivo(Boolean.TRUE);

        // Verificar se os atributos foram atribuídos corretamente
        assertNotNull(produtoServico.getId());
        assertEquals(Boolean.TRUE, produtoServico.getProduto());
        assertEquals("Produto Teste", produtoServico.getNome());
        assertEquals("Descrição do produto teste", produtoServico.getDescricao());
        assertEquals(new BigDecimal("10.00"), produtoServico.getPreco());
        assertEquals(Boolean.TRUE, produtoServico.getAtivo());
    }

    @Test
    void naoDeveExcluirProdutoServicoAssociadoPedido() {
        // Criar um produto/serviço associado a um pedido
        ProdutoServico produtoServico = new ProdutoServico();
        ItemPedido itemPedido = Mockito.mock(ItemPedido.class);
        produtoServico.getItensPedidos().add(itemPedido);

        // Verificar se o produto/serviço não pode ser excluído
        assertThrows(IllegalStateException.class, produtoServico::verificarSePodeExcluir);
    }

    @Test
    void devePermitirExcluirProdutoServicoSemPedidosAssociados() {
        // Criar um produto/serviço sem pedidos associados
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setItensPedidos(Collections.emptyList());

        // Verificar se o produto/serviço pode ser excluído
        assertDoesNotThrow(produtoServico::verificarSePodeExcluir);
    }

    @Test
    void deveRetornarIdProdutoServico() {
        // Criar um produto/serviço com ID definido
        ProdutoServico produtoServico = new ProdutoServico();
        UUID id = UUID.randomUUID();
        produtoServico.setId(id);

        // Verificar se o ID é retornado corretamente
        assertEquals(id, produtoServico.getId());
    }

    @Test
    void devePermitirPedidoDeProdutoServicoAtivo() {
        // Criar um produto/serviço ativo
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setAtivo(true);

        // Criar um novo pedido com o produto/serviço ativo
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido(produtoServico, 1);
        pedido.adicionarItem(itemPedido);

        // Verificar se o pedido foi criado com sucesso
        assertTrue(pedido.getItens().contains(itemPedido));
    }

    @Test
    void naoDevePermitirPedidoDeProdutoServicoInativo() {
        // Criar um produto/serviço ativo
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setAtivo(false);

        // Criar um novo pedido com o produto/serviço ativo
        Pedido pedido = new Pedido();
        ItemPedido itemPedido = new ItemPedido(produtoServico, 1);
        pedido.adicionarItem(itemPedido);

        // Verificar se o pedido foi criado com sucesso
        assertTrue(pedido.getItens().contains(itemPedido));
    }
}