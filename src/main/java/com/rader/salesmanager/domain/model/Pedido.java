package com.rader.salesmanager.domain.model;

import com.rader.salesmanager.domain.event.PedidoCanceladoEvent;
import com.rader.salesmanager.domain.event.PedidoFechadoEvent;
import com.rader.salesmanager.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    private BigDecimal valorTotalServicos;
    private BigDecimal valorTotalProdutos;
    private BigDecimal subTotal;
    private BigDecimal desconto;
    private BigDecimal valorTotal;

    private StatusPedido status = StatusPedido.ABERTO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {

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

    public void abrir(){
        this.setDesconto(BigDecimal.ZERO);
    }

    public void fechar(){
        this.setStatus(StatusPedido.FECHADO);
        registerEvent(new PedidoFechadoEvent(this));
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        registerEvent(new PedidoCanceladoEvent(this));
    }

    public boolean podeSerFechado(){
        return getStatus().podeAlterarPara(StatusPedido.FECHADO);
    }

    public boolean podeSerCancelado(){
        return getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    public boolean podeDarDesconto() {return getStatus() == StatusPedido.ABERTO;}

    public void aplicarDesconto(BigDecimal percentualDesconto) {
        if (status != StatusPedido.ABERTO) {
            throw new NegocioException("N??o ?? poss??vel aplicar desconto em um pedido que n??o est?? com status aberto.");
        }
        var valorDescontoProdutos = getValorTotalProdutos().multiply(percentualDesconto.divide(BigDecimal.valueOf(100)));

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
        valorTotal = (valorTotal == null) ? BigDecimal.ZERO : valorTotal;
        valorTotalProdutos = (valorTotalProdutos == null) ? BigDecimal.ZERO : valorTotalProdutos;
        valorTotalServicos = (valorTotalServicos == null) ? BigDecimal.ZERO : valorTotalServicos;
        subTotal = (subTotal == null) ? BigDecimal.ZERO : subTotal;
        desconto = (desconto == null) ? BigDecimal.ZERO : desconto;

        getItens().forEach(ItemPedido::calcularPrecoTotal);
        valorTotalProdutos = getValorTotalProdutos();
        valorTotalServicos = getValorTotalServicos();
        subTotal = valorTotalProdutos.add(valorTotalServicos);
        valorTotal = valorTotalProdutos.add(valorTotalServicos).subtract(desconto);
    }

    public void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(
                    String.format("Status do pedido n??o pode ser alterado de %s para %s",
                            getId(), getStatus().getDescricao(),
                            novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }
}
