package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.PedidoController;
import com.rader.salesmanager.api.model.ItemPedidoResumoModel;
import com.rader.salesmanager.api.model.PedidoModel;
import com.rader.salesmanager.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesLinks salesLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoModel);

        List<ItemPedidoResumoModel> produtos = new ArrayList<>();
        List<ItemPedidoResumoModel> servicos = new ArrayList<>();

        for (ItemPedidoResumoModel item : pedidoModel.getItens()) {
            if (item.getProdutoServico().getProduto()) {
                produtos.add(item);
            } else {
                servicos.add(item);
            }
        }

        pedidoModel.setProdutos(produtos);
        pedidoModel.setServicos(servicos);

        pedidoModel.add(salesLinks.linkToPedidos("pedidos"));
        if (pedido.podeSerFechado()) {
            pedidoModel.add(salesLinks.linkToFechamentoPedido(pedido.getId().toString(), "fechar"));
        }
        if (pedido.podeSerCancelado()) {
            pedidoModel.add(salesLinks.linkToCancelamentoPedido(pedido.getId().toString(), "cancelar"));
        }
        if (pedido.podeDarDesconto()) {
            pedidoModel.add(salesLinks.linkToDescontoPedido(pedido.getId().toString(), "descontar"));
        }

        pedidoModel.getItens().forEach(item -> {
            item.add(salesLinks.linkToItemPedido(item.getId(), "item"));

            if (item.getProdutoServico().getProduto()) {
                item.add(salesLinks.linkToPedidos( "pedidos"));
                item.getProdutoServico().add(salesLinks.linkToProduto(item.getProdutoServico().getId(), "produto"));
            } else {
                item.add(salesLinks.linkToPedidos( "pedidos"));
                item.getProdutoServico().add(salesLinks.linkToServico(item.getProdutoServico().getId(), "servico"));
            }
        });

        return pedidoModel;
    }
}

