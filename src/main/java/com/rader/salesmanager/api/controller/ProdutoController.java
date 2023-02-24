package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.assembler.ProdutoInputDisassembler;
import com.rader.salesmanager.api.assembler.ProdutoModelAssembler;
import com.rader.salesmanager.api.model.ProdutoModel;
import com.rader.salesmanager.api.model.input.ProdutoInput;
import com.rader.salesmanager.api.openapi.controller.ProdutoControllerOpenApi;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController implements ProdutoControllerOpenApi {

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Autowired
    PagedResourcesAssembler<ProdutoServico> pagedResourcesAssembler;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscar(@PathVariable UUID id) {
        return produtoModelAssembler.toModel(cadastroProduto.buscarOuFalhar(id));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@RequestBody @Valid ProdutoInput produtoInput) {
        ProdutoServico produto = produtoInputDisassembler.toDomainObject(produtoInput);

        return produtoModelAssembler
                .toModel(cadastroProduto.salvar(produto));
    }

    @Override
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel atualizar(@PathVariable UUID id, @RequestBody @Valid ProdutoInput produtoInput) {
        ProdutoServico produto = cadastroProduto.buscarOuFalhar(id);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produto);

        return produtoModelAssembler
                .toModel(cadastroProduto.salvar(produto));
    }
}
