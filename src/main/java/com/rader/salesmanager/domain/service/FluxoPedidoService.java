package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class FluxoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private CadastroProdutoServicoService psService;

    @Transactional
    public void darDesconto (UUID idPedido, BigDecimal pctDesconto){
        var pedido = emissaoPedido.buscarOuFalhar(idPedido);
        pedido.aplicarDesconto(pctDesconto);
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar (UUID idPedido){
        var pedido = emissaoPedido.buscarOuFalhar(idPedido);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void fechar (UUID idPedido){
        var pedido = emissaoPedido.buscarOuFalhar(idPedido);
        pedido.fechar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void adicionarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
                    ProdutoServico produto = psService
                            .buscarOuFalhar(item.getProdutoServico().getId());

                    item.setProdutoServico(produto);
                    item.setPrecoUnitario(produto.getPreco());
                    pedido.adicionarItem(item);
        });
        pedido.calcularValorTotal();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void removerItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            ProdutoServico produto = psService
                    .buscarOuFalhar(item.getProdutoServico().getId());
            pedido.removerItem(item);
        });
        pedidoRepository.save(pedido);

    }

}
