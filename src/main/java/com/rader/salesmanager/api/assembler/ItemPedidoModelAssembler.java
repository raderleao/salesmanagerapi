package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.ItemPedidoController;
import com.rader.salesmanager.api.model.ItemPedidoModel;
import com.rader.salesmanager.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoModelAssembler extends RepresentationModelAssemblerSupport<ItemPedido, ItemPedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesLinks salesLinks;

    public ItemPedidoModelAssembler() {
        super(ItemPedidoController.class, ItemPedidoModel.class);
    }

    @Override
    public ItemPedidoModel toModel(ItemPedido itemPedido) {
        ItemPedidoModel itemPedidoModel = createModelWithId(itemPedido.getId(), itemPedido);

        modelMapper.map(itemPedido, itemPedidoModel);

        itemPedidoModel.add(salesLinks.linkToItemPedido(itemPedidoModel.getId(), "itemPedido"));


        return itemPedidoModel;
    }
}

