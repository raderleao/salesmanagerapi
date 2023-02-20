package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.PedidoNaoEncontradoException;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoServicoService psService;

    @Autowired
    private CadastroProdutoService cadastroProduto;


    @Transactional
    public Pedido emitir(Pedido pedido) {
        pedido.abrir();
        validarItens(pedido);

        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido fechar(Pedido pedido) {
        pedido.fechar();
        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido){

        pedido.getItens().forEach(item -> {
            ProdutoServico produtoServico = psService.buscarOuFalhar(item.getProdutoServico().getId());

            item.setPedido(pedido);
            item.setProdutoServico(produtoServico);
            item.setPrecoUnitario(produtoServico.getPreco());
        });
    }

    @Transactional
    public void adicionarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            ProdutoServico produto = cadastroProduto.buscarOuFalhar(item.getProdutoServico().getId());

            item.setProdutoServico(produto);
            item.setPrecoUnitario(produto.getPreco());

            pedido.adicionarItem(item);
        });
        pedido.calcularValorTotal();
    }

    @Transactional
    public void removerItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            ProdutoServico produto = cadastroProduto.buscarOuFalhar(item.getProdutoServico().getId());
            pedido.removerItem(item);
        });
    }

    @Transactional
    public void darDesconto(Pedido pedido, BigDecimal percentualDesconto){
        buscarOuFalhar(pedido.getId());
        pedido.aplicarDesconto(percentualDesconto);
    }

    public Page<Pedido> buscarTodos(
            Specification<Pedido> filtro, Pageable pageable) {

        return pedidoRepository.findAll(filtro, pageable);
    }

   /* private void calcularTotais(Pedido pedido) {
        var pedidoCalculado = calcularTotaisdeProdutoEServico(pedido);

        BigDecimal desconto = pedido.getDesconto() != null ? pedido.getDesconto() : BigDecimal.ZERO;

        if (!desconto.equals(BigDecimal.ZERO)) {
            pedidoCalculado = darDesconto(pedidoCalculado, desconto);
        }
        pedidoRepository.save(pedidoCalculado);
    }


    private Pedido calcularTotaisdeProdutoEServico(Pedido pedido){
        BigDecimal valorTotalProdutos = BigDecimal.ZERO;
        BigDecimal valorTotalServicos = BigDecimal.ZERO;

        for (ItemPedido item : pedido.getItens()) {
            ProdutoServico produtoServico = item.getProdutoServico();
            BigDecimal valorItem = item.getValorTotal();

            if (produtoServico.getProduto()) {
                valorTotalProdutos = valorTotalProdutos.add(valorItem);
            } else {
                valorTotalServicos = valorTotalServicos.add(valorItem);
            }
        }
        var totalProdutosServicos = valorTotalProdutos.add(valorTotalServicos);

        pedido.setValorTotalProdutos(valorTotalProdutos);
        pedido.setValorTotalServicos(valorTotalServicos);
        pedido.setSubTotal(totalProdutosServicos);
        pedido.setValorTotal(totalProdutosServicos);

        return pedido;
    }

    private Pedido darDesconto (Pedido pedido, BigDecimal percentDesconto) {
        if (pedido.getStatus().equals(StatusPedido.FECHADO))
            throw new IllegalStateException("Não é possível aplicar desconto em um pedido que não está aberto.");

        var valorProdutos = pedido.getValorTotalProdutos();

        var novoValorTotalProdutos = valorProdutos.multiply(
                    percentDesconto.divide(BigDecimal.valueOf(100)));

            pedido.setValorTotalProdutosDesconto(novoValorTotalProdutos);
            pedido.setValorTotal(novoValorTotalProdutos.add(pedido.getValorTotalServicos()));
            return pedidoRepository.save(pedido);
    }*/

    public Pedido buscarOuFalhar(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id.toString()));
    }

}
