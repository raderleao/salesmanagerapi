package com.rader.salesmanager.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal precoUnitario;

    private Integer quantidade;

    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_servico_id")
    private ProdutoServico produtoServico;

    public void atualizarQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        this.valorTotal = precoUnitario.multiply(
                new BigDecimal(BigInteger.valueOf(quantidade), 2));
        if (pedido != null) {
            pedido.getValorTotal();
        }
    }

}
