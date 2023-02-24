package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.ItemPedidoModel;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Api(tags = "Pedidos")
public interface ItemPedidoControllerOpenApi {

    @ApiOperation("Busca um ItemPedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ItemPedidoModel buscar(
            @ApiParam(value = "Código de um itempedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
            UUID id);

}
