package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.assembler.PedidoInputDisassembler;
import com.rader.salesmanager.api.assembler.PedidoModelAssembler;
import com.rader.salesmanager.api.model.PedidoModel;
import com.rader.salesmanager.api.model.input.PedidoDescontoInput;
import com.rader.salesmanager.api.model.input.PedidoInput;
import com.rader.salesmanager.api.openapi.controller.PedidoControllerOpenApi;
import com.rader.salesmanager.core.data.PageWrapper;
import com.rader.salesmanager.core.data.PageableTranslator;
import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.exception.NegocioException;
import com.rader.salesmanager.domain.filter.PedidoFilter;
import com.rader.salesmanager.domain.model.Pedido;
import com.rader.salesmanager.domain.service.EmissaoPedidoService;
import com.rader.salesmanager.domain.service.FluxoPedidoService;
import com.rader.salesmanager.infrastructure.repository.spec.PedidoSpecs;
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
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private EmissaoPedidoService cadastroPedido;

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @Autowired
    PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private SalesLinks salesLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<PedidoModel> pesquisar(PedidoFilter filtro,
                                             @PageableDefault(size = 10) Pageable pageable){
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = cadastroPedido.buscarTodos(
                PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoModelAssembler);
    }

    @Override
    @GetMapping("/{id}")
    public PedidoModel buscar(@PathVariable UUID id) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(id);
        return pedidoModelAssembler.toModel(pedido);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido = cadastroPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping(path = "{id}/desconto", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> descontar(@PathVariable String id,
                                          @RequestBody @Valid PedidoDescontoInput descontoInput){
        var uuIdPedido = UUID.fromString(id);

        var pctDesconto = descontoInput.getDesconto();
        fluxoPedido.darDesconto(uuIdPedido,pctDesconto);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping(path = "{id}/fechamento", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable String id ){

        var uuIdPedido = UUID.fromString(id);
        fluxoPedido.fechar(uuIdPedido);

        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping(path = "{id}/cancelamento", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String id ){

        var uuIdPedido = UUID.fromString(id);
        fluxoPedido.cancelar(uuIdPedido);

        return ResponseEntity.noContent().build();
    }


    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "id", "id",
                "subtotal", "subtotal",
                "valorTotalProdutos", "valorTotalProdutos",
                "valorTotalServicos", "valorTotalServicos",
                "desconto", "desconto",
                "valorTotal", "valorTotal"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
