package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.ServicoModel;
import com.rader.salesmanager.api.model.input.ServicoInput;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "Serviços")
public interface ServicoControllerOpenApi {

    @ApiOperation("Pesquisa os servicos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    PagedModel<ServicoModel> pesquisar(ProdutoFilter filtro,
                                       @PageableDefault(size = 10) Pageable pageable);


    @ApiOperation("Busca um serviço")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do serviço inválido", response = Problem.class),
    })
    ServicoModel buscar(

            @ApiParam(value = "ID do serviço", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID servicoId);

    @ApiOperation("Cadastra um serviço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Serviço cadastrado"),
    })
    ServicoModel adicionar(

            @ApiParam(name = "corpo", value = "Representação de um novo serviço", required = true)
            ServicoInput servicoInput);

    @ApiOperation("Atualiza um serviço")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço atualizado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    ServicoModel atualizar(

            @ApiParam(value = "ID do serviço", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID servicoId,

            @ApiParam(name = "corpo", value = "Representação de um serviço com os novos dados",
                    required = true)
           ServicoInput servicoInput);

    @ApiOperation("Exclui um serviço por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Serviço excluído"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um Serviço", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            UUID servicoId);

    @ApiOperation("Ativa um servico por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço ativado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    @PutMapping(path ="/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(
            @ApiParam(value = "ID de um Serviço", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            @PathVariable UUID id);


    @ApiOperation("Inativa um serviço por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço inativado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    @DeleteMapping(path ="/{Id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(
            @ApiParam(value = "ID de um Serviço", example = "56f784d6-2c44-47c6-9065-685e5c5d5e5a", required = true)
            @PathVariable UUID id);

}
