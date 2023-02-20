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

@Transactional
@Service
public class CadastroPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoServicoService psService;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    public Pedido emitir(Pedido pedido) {
        pedido.abrir();
        validarItens(pedido);

        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    public Pedido fechar(Pedido pedido) {
        pedido.fechar();
        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido){

        pedido.getItens().forEach(item -> {
            ProdutoServico produtoServico = psService
                    .buscarOuFalhar(item.getProdutoServico().getId());

            item.setPedido(pedido);
            item.setProdutoServico(produtoServico);
            item.setPrecoUnitario(produtoServico.getPreco());
        });
    }

    public void adicionarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            ProdutoServico produto = cadastroProduto
                    .buscarOuFalhar(item.getProdutoServico().getId());

            item.setProdutoServico(produto);
            item.setPrecoUnitario(produto.getPreco());

            pedido.adicionarItem(item);
        });
        pedido.calcularValorTotal();
    }

    public void removerItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            ProdutoServico produto = cadastroProduto
                    .buscarOuFalhar(item.getProdutoServico().getId());
            pedido.removerItem(item);
        });
    }

    public void darDesconto(Pedido pedido, BigDecimal percentualDesconto){
        buscarOuFalhar(pedido.getId());
        pedido.aplicarDesconto(percentualDesconto);
    }

    public Page<Pedido> buscarTodos(
            Specification<Pedido> filtro, Pageable pageable) {

        return pedidoRepository.findAll(filtro, pageable);
    }

    public Pedido buscarOuFalhar(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id.toString()));
    }

}
