package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.assembler.ProdutoServicoModelAssembler;
import com.rader.salesmanager.api.model.ProdutoServicoModel;
import com.rader.salesmanager.api.openapi.controller.ProdutoServicoControllerOpenApi;
import com.rader.salesmanager.core.data.PageWrapper;
import com.rader.salesmanager.core.data.PageableTranslator;
import com.rader.salesmanager.domain.exception.NegocioException;
import com.rader.salesmanager.domain.exception.ProdutoServicoNaoEncontradoException;
import com.rader.salesmanager.domain.filter.ProdutoServicoFilter;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroProdutoServicoService;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/produtos-servicos")
public class ProdutoServicoController implements ProdutoServicoControllerOpenApi {

    @Autowired
    private ProdutoServicoModelAssembler psModelAssembler;

    @Autowired
    private CadastroProdutoServicoService psService;

    @Autowired
    PagedResourcesAssembler<ProdutoServico> pagedResourcesAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<ProdutoServicoModel> pesquisar(ProdutoServicoFilter filtro,
                                                     @PageableDefault(size = 10) Pageable pageable){
        Pageable pageableTraduzido = traduzirPageable(pageable);

        Page<ProdutoServico> psPage = psService.buscarTodos(
                ProdutoServicoSpecs.usandoFiltro(filtro), pageableTraduzido);

        psPage = new PageWrapper<>(psPage, pageable);

        return pagedResourcesAssembler.toModel(psPage, psModelAssembler);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoServicoModel buscar(@PathVariable UUID id) {
        return psModelAssembler.toModel(psService.buscarOuFalhar(id));
    }

    @Override
    @PutMapping(path ="/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable String id) {
        psService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(path ="/{id}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable String id) {
        psService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<String> psId ){
        try {
            psService.ativar(psId);
        } catch (ProdutoServicoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        psService.excluir(id);
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
