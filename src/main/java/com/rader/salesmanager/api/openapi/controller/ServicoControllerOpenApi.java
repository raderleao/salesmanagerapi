package com.rader.salesmanager.api.openapi.controller;

import com.rader.salesmanager.api.exceptionhandler.Problem;
import com.rader.salesmanager.api.model.ServicoModel;
import com.rader.salesmanager.api.model.input.ServicoInput;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@Api(tags = "Serviços")
public interface ServicoControllerOpenApi {

    @ApiOperation("Busca um serviço")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do serviço inválido", response = Problem.class),
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ServicoModel buscar(

            @ApiParam(value = "ID do serviço", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID Id);

    @ApiOperation("Cadastra um serviço")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Serviço cadastrado"),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ServicoModel adicionar(

            @ApiParam(name = "corpo", value = "Representação de um novo serviço", required = true)
            ServicoInput servicoInput);


    @ApiOperation("Atualiza um servico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Serviço atualizado"),
            @ApiResponse(code = 404, message = "Serviço não encontrado", response = Problem.class)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ServicoModel atualizar(

            @ApiParam(value = "ID do serviço", example = "9b6d2a6a-4626-4267-98b5-8d704e26245b", required = true)
            UUID Id,

            @ApiParam(name = "corpo", value = "Representação de um serviço com os novos dados",
                    required = true)
            ServicoInput servicoInput);


}
