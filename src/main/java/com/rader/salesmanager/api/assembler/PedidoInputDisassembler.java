package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.model.input.PedidoInput;
import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public Pedido toDomainObject(PedidoInput pedidoInput) {
       var itensPedidos =
        pedidoInput.getItens()
                .stream()
                .map(itemPedidoInput -> {
                    var itemPedido = new ItemPedido();
                    var produtoService = new ProdutoServico();

                    produtoService.setId(itemPedidoInput.getId());
                    itemPedido.setProdutoServico(produtoService);
                    itemPedido.setQuantidade(itemPedidoInput.getQuantidade());
                    return itemPedido;
                }).collect(Collectors.toList());

        var pedido = new Pedido();
        pedido.setItens(itensPedidos);

        return pedido;
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {

        modelMapper.map(pedidoInput, pedido);
    }

}
