package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.ProdutoServicoModel;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(tags = "Produtos ou Serviços")
public interface ProdutoServicoControllerOpenApi {

    @ApiOperation("Pesquisa os produtos e serviços")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PagedModel<ProdutoServicoModel> pesquisar(ProdutoFilter filtro,
                                              @PageableDefault(size = 10) Pageable pageable);


    @ApiOperation("Busca um produto ou serviço")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do serviço inválido", response = Problem.class),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProdutoServicoModel buscar(

            @ApiParam(value = "ID de um produto ou serviço", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID Id);


    @ApiOperation("Ativa um produto ou serviço por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço ativado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    @PutMapping(path ="/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um Serviço", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            @PathVariable String id);


    @ApiOperation("Inativa produto ou serviço por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Servico inativado"),
            @ApiResponse(code = 404, message = "Servico não encontrado", response = Problem.class)
    })
    @DeleteMapping(path ="/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um Servico", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            @PathVariable String id);

    @ApiOperation("Ativa multiplos produtos ou serviços por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto(s) ou Serviço(s) ativados"),
            @ApiResponse(code = 404, message = "Produto(s) ou Serviço(s) não encontrados", response = Problem.class)
    })
    @PutMapping(path ="/{id}/ativacoes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(
            @ApiParam(value = "Lista de ID(s) de Produto(s) ou Serviço(s)", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            @PathVariable List<String> ids);

    @ApiOperation("Exclui um Produto ou Serviço por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto ou Serviço excluído(a)"),
            @ApiResponse(code = 404, message = "Produto ou Serviço não encontrado(a)", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    void remover(
            @ApiParam(value = "ID de um Produto ou Serviço", example = "1", required = true)
            UUID Id);

}
