package com.rader.salesmanager.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal precoTotal;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_servico_id")
    private ProdutoServico produtoServico;

    public void calcularPrecoTotal() {
        BigDecimal precoUnitario = this.getPrecoUnitario();
        Integer quantidade = this.getQuantidade();

        if (precoUnitario == null) {precoUnitario = BigDecimal.ZERO;}
        if (quantidade == null) {quantidade = 0;}

        this.setPrecoTotal(precoUnitario.multiply(
                    new BigDecimal(quantidade)));
    }

}
