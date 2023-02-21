package com.rader.salesmanager.api;

import com.rader.salesmanager.api.controller.FluxoPedidoController;
import com.rader.salesmanager.api.controller.PedidoController;
import com.rader.salesmanager.api.controller.ProdutoController;
import org.springframework.hateoas.*;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SalesLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("status", VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToFechamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .fechar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToDescontoPedido(String idPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .descontar(idPedido, null)).slash(idPedido).withRel(rel);
    }

    public Link linkToProduto(String produtoId, String rel) {
        return linkTo(methodOn(ProdutoController.class)
                .buscar(UUID.fromString(produtoId)))
                .withRel(rel);
    }

    public Link linkToProduto(String produtoId) {
        return linkToProduto(produtoId, IanaLinkRelations.SELF.value());
    }

}
