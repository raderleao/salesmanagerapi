package com.rader.salesmanager.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Pedido {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal valorTotalServicos;
    private BigDecimal valorTotalProdutos;
    private BigDecimal subTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotalProdutosDesconto;
    private BigDecimal valorTotal;

    private StatusPedido status = StatusPedido.ABERTO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {
        if (!item.getProdutoServico().getAtivo()) {
            throw new IllegalArgumentException("Não é possível adicionar um produto ou serviço desativado em um pedido.");
        }
        itens.add(item);
        item.setPedido(this);
        calcularValorTotal();
    }

    public void removerItem(ItemPedido item) {
        if (itens.contains(item)) {
            itens.remove(item);
            item.setPedido(null);
            calcularValorTotal();
        }
    }

    public void fechar(){
        this.setStatus(StatusPedido.FECHADO);
    }
    public void aplicarDesconto(BigDecimal percentualDesconto) {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Não é possível aplicar desconto em um pedido que não está aberto.");
        }
        BigDecimal valorDescontoProdutos = getValorTotalProdutos().multiply(percentualDesconto.divide(BigDecimal.valueOf(100)));
        for (ItemPedido item : itens) {
            if (item.getProdutoServico().getProduto()) {
                BigDecimal valorDescontoItem = item.getPrecoUnitario().multiply(percentualDesconto.divide(BigDecimal.valueOf(100)));
                item.setPrecoUnitario(item.getPrecoUnitario().subtract(valorDescontoItem));
            }
        }
        desconto = valorDescontoProdutos;
        calcularValorTotal();
    }

    public BigDecimal getValorTotalProdutos() {
        return itens.stream()
                .filter(item -> item.getProdutoServico().getProduto())
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getValorTotalServicos() {
        return itens.stream()
                .filter(item -> !item.getProdutoServico().getProduto())
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public void calcularValorTotal() {
        getItens().forEach(ItemPedido::calcularPrecoTotal);
        valorTotalProdutos = getValorTotalProdutos();
        valorTotalServicos = getValorTotalServicos();
        subTotal = valorTotalProdutos.add(valorTotalServicos);
        valorTotal = valorTotalProdutos.add(valorTotalServicos);
    }
}
