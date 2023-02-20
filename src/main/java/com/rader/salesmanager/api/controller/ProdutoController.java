package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.assembler.ProdutoInputDisassembler;
import com.rader.salesmanager.api.assembler.ProdutoModelAssembler;
import com.rader.salesmanager.api.model.ProdutoModel;
import com.rader.salesmanager.api.model.input.ProdutoInput;
import com.rader.salesmanager.api.openapi.controller.ProdutoControllerOpenApi;
import com.rader.salesmanager.core.data.PageWrapper;
import com.rader.salesmanager.core.data.PageableTranslator;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroProdutoService;
import com.rader.salesmanager.infrastructure.repository.spec.ProdutoServicoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
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

    @Autowired
    private SalesLinks salesLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<ProdutoModel> pesquisar(ProdutoFilter filtro,
                                              @PageableDefault(size = 10) Pageable pageable){
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<ProdutoServico> produtosPage = cadastroProduto.buscarTodos(
                ProdutoServicoSpecs.usandoFiltro(filtro), pageableTraduzido);

        produtosPage = new PageWrapper<>(produtosPage, pageable);

        return pagedResourcesAssembler.toModel(produtosPage, produtoModelAssembler);
    }

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

    @Override
    @PutMapping(path ="/{produtoId}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable UUID id) {
        cadastroProduto.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path ="/{produtoId}/inativar", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable UUID id) {
        cadastroProduto.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        cadastroProduto.excluir(id);
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "id", "id",
                "nome", "nome",
                "descricao", "descricao",
                "preco", "preco",
                "ativo", "ativo",
                "produto", "produto"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
