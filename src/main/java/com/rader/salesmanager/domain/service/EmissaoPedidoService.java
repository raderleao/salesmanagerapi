package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.PedidoNaoEncontradoException;
import com.rader.salesmanager.domain.exception.ProdutoServicoNaoAtivoException;
import com.rader.salesmanager.domain.exception.ProdutoServicoNaoEncontradoException;
import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ItemPedidoRepository;
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
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroProdutoServicoService psService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    public Pedido emitir(Pedido pedido) {
        pedido.abrir();
        validarItens(pedido);
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    private void validarItens(Pedido pedido){
        if(pedido.getDesconto() == null) pedido.setDesconto(BigDecimal.ZERO);


        pedido.getItens().forEach(item -> {
            ProdutoServico produtoServico = psService
                    .buscarOuFalhar(item.getProdutoServico().getId());
            if(!produtoServico.getAtivo()) {
                throw new ProdutoServicoNaoAtivoException(produtoServico.getId());
            }
            item.setPedido(pedido);
            item.setProdutoServico(produtoServico);
            item.setPrecoUnitario(produtoServico.getPreco());

        });

        if(pedido.getDesconto() == null) pedido.setDesconto(BigDecimal.ZERO);
    }

    public Page<Pedido> buscarTodos(
            Specification<Pedido> filtro, Pageable pageable) {

        return pedidoRepository.findAll(filtro, pageable);
    }

    public Pedido buscarOuFalhar(UUID id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id.toString()));
    }

    public ItemPedido buscarOuFalharItemPedido(UUID id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ProdutoServicoNaoEncontradoException(id.toString()));
    }
}
