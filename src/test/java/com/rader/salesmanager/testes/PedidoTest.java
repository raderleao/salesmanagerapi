package com.rader.salesmanager.testes;

import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PedidoTest {

    @Test
    public void testCriarPedido() {
        // Criar um novo pedido
        Pedido pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setValorTotalServicos(new BigDecimal("0"));
        pedido.setValorTotalProdutos(new BigDecimal("0"));
        pedido.setSubTotal(new BigDecimal("0"));
        pedido.setDesconto(new BigDecimal("0"));
        pedido.setValorTotal(new BigDecimal("0"));
        pedido.setItens(new ArrayList<ItemPedido>());

        // Verificar se os atributos foram atribuídos corretamente
        assertNotNull(pedido.getId());
        assertEquals(new BigDecimal("0"), pedido.getValorTotalServicos());
        assertEquals(new BigDecimal("0"), pedido.getValorTotalProdutos());
        assertEquals(new BigDecimal("0"), pedido.getSubTotal());
        assertEquals(new BigDecimal("0"), pedido.getDesconto());
        assertEquals(new BigDecimal("0"), pedido.getValorTotal());
        assertEquals(StatusPedido.ABERTO, pedido.getStatus());
        assertNotNull(pedido.getItens());
        assertEquals(0, pedido.getItens().size());
    }
}

