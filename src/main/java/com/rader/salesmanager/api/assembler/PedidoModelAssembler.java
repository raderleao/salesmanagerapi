package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.PedidoController;
import com.rader.salesmanager.api.model.PedidoModel;
import com.rader.salesmanager.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

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

       /* pedidoModel.add(salesLinks.linkToPedidos("pedidos"));

        if(pedido.podeSerConfirmado()) {
            pedidoModel.add(salesLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
        }
        if(pedido.podeSerCancelado()) {
            pedidoModel.add(salesLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
        }
        if(pedido.podeSerEntregue()) {
            pedidoModel.add(salesLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
        }*/


/*
pedidoModel.getItens().forEach(item -> {
            item.add(salesLinks.linkToProduto(item.getProdutoServicoId(), "produto"));
        });*/


        return pedidoModel;
    }

}
