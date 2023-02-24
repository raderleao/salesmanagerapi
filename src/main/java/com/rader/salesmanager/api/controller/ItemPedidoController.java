package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.assembler.ItemPedidoModelAssembler;
import com.rader.salesmanager.api.model.ItemPedidoModel;
import com.rader.salesmanager.api.openapi.controller.ItemPedidoControllerOpenApi;
import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.service.CadastroItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/itenspedidos")
public class ItemPedidoController implements ItemPedidoControllerOpenApi {

    @Autowired
    private ItemPedidoModelAssembler itemPedidoModelAssembler;

    @Autowired
    private CadastroItemPedidoService cadastroItemPedido;

    @Override
    @GetMapping("/{id}")
    public ItemPedidoModel buscar(@PathVariable UUID id) {
        ItemPedido itemPedido = cadastroItemPedido.buscarOuFalhar(id);
        return itemPedidoModelAssembler.toModel(itemPedido);
    }

}
