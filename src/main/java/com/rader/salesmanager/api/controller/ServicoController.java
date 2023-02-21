package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.assembler.ServicoInputDisassembler;
import com.rader.salesmanager.api.assembler.ServicoModelAssembler;
import com.rader.salesmanager.api.model.ServicoModel;
import com.rader.salesmanager.api.model.input.ServicoInput;
import com.rader.salesmanager.api.openapi.controller.ServicoControllerOpenApi;
import com.rader.salesmanager.core.data.PageWrapper;
import com.rader.salesmanager.core.data.PageableTranslator;
import com.rader.salesmanager.domain.filter.ProdutoFilter;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroServicoService;
import com.rader.salesmanager.infrastructure.repository.spec.ProdutoServicoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/servicos")
public class ServicoController implements ServicoControllerOpenApi {

    @Autowired
    private ServicoModelAssembler servicoModelAssembler;

    @Autowired
    private CadastroServicoService cadastroServico;

    @Autowired
    private ServicoInputDisassembler servicoInputDisassembler;

    @Autowired
    PagedResourcesAssembler<ProdutoServico> pagedResourcesAssembler;

    @Autowired
    private SalesLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<ServicoModel> pesquisar(ProdutoFilter filtro,
                                              @PageableDefault(size = 10) Pageable pageable){
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<ProdutoServico> servicosPage = cadastroServico.buscarTodos(
                ProdutoServicoSpecs.usandoFiltro(filtro), pageableTraduzido);

        servicosPage = new PageWrapper<>(servicosPage, pageable);

        return pagedResourcesAssembler.toModel(servicosPage, servicoModelAssembler);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServicoModel buscar(@PathVariable UUID id) {
        return servicoModelAssembler.toModel(cadastroServico.buscarOuFalhar(id));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoModel adicionar(@RequestBody @Valid ServicoInput servicoInput) {
        ProdutoServico servico = servicoInputDisassembler.toDomainObject(servicoInput);

        return servicoModelAssembler
                .toModel(cadastroServico.salvar(servico));
    }

    @Override
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ServicoModel atualizar(@PathVariable UUID id, @RequestBody @Valid ServicoInput servicoInput) {
        ProdutoServico servico = cadastroServico.buscarOuFalhar(id);
        servicoInputDisassembler.copyToDomainObject(servicoInput, servico);

        return servicoModelAssembler
                .toModel(cadastroServico.salvar(servico));
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
