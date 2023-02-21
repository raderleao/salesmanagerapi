package com.rader.salesmanager.testes;

import com.rader.salesmanager.domain.model.ItemPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemPedidoTest {

    @Test
    public void testCriarItemPedido() {
        // Criar um novo item de pedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(UUID.randomUUID());
        itemPedido.setPrecoUnitario(new BigDecimal("10.00"));
        itemPedido.setQuantidade(2);
        itemPedido.setPrecoTotal(new BigDecimal("20.00"));

        // Verificar se os atributos foram atribuídos corretamente
        assertNotNull(itemPedido.getId());
        assertEquals(new BigDecimal("10.00"), itemPedido.getPrecoUnitario());
        assertEquals(Integer.valueOf(2), itemPedido.getQuantidade());
        assertEquals(new BigDecimal("20.00"), itemPedido.getPrecoTotal());
    }
}

