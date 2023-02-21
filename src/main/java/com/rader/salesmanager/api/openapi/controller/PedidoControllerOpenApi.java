package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.PedidoModel;
import com.rader.salesmanager.api.model.input.PedidoDescontoInput;
import com.rader.salesmanager.api.model.input.PedidoInput;
import com.rader.salesmanager.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Pesquisa os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PagedModel<PedidoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PedidoModel buscar(
            @ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
            UUID id);


    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    PedidoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
            PedidoInput pedidoInput);


    @ApiOperation("Fechamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido fechado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> descontar(
            @ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
            String id,
            @ApiParam(name = "corpo", value = "Representação de um pedido com os novos dados",
            required = true)
            PedidoDescontoInput descontoInput);

    @ApiOperation("Fechamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido fechado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> fechar(
            @ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
            String id);


    @ApiOperation("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> cancelar(
            @ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
                    required = true)
            String id);
}
