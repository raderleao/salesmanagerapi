package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.ProdutoModel;
import com.rader.salesmanager.api.model.input.ProdutoInput;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@Api(tags = "Produtos")
public interface ProdutoControllerOpenApi {

    @ApiOperation("Pesquisa os produtos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PagedModel<ProdutoModel> pesquisar(ProdutoFilter filtro,
                                       @PageableDefault(size = 10) Pageable pageable);


    @ApiOperation("Busca um produto")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do produto inválido", response = Problem.class),
    })
    ProdutoModel buscar(

            @ApiParam(value = "ID do produto", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID Id);

    @ApiOperation("Cadastra um produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
    })
    ProdutoModel adicionar(

            @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true)
            ProdutoInput produtoInput);


    @ApiOperation("Atualiza um produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado", response = Problem.class)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProdutoModel atualizar(

            @ApiParam(value = "ID do produto", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID Id,

            @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados",
                    required = true)
            ProdutoInput produtoInput);

}
