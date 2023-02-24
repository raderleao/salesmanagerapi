package com.rader.salesmanager.api;

import com.rader.salesmanager.api.controller.*;
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
        return linkTo(methodOn(PedidoController.class)
                .fechar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(PedidoController.class)
                .cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToDescontoPedido(String idPedido, String rel) {
        return linkTo(methodOn(PedidoController.class)
                .descontar(idPedido, null)).withRel(rel);
    }

    //Item Pedido
    public Link linkToItemPedido(UUID id, String rel) {
        return linkTo(methodOn(ItemPedidoController.class)
                .buscar(id)).withRel(rel);
    }

    //Produtos e Serviços
    public Link linkToProdutosServicos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("ativo", VariableType.REQUEST_PARAM),
                new TemplateVariable("produto", VariableType.REQUEST_PARAM),
                new TemplateVariable("nome", VariableType.REQUEST_PARAM),
                new TemplateVariable("precoInicial", VariableType.REQUEST_PARAM),
                new TemplateVariable("precoFinal", VariableType.REQUEST_PARAM));


        String pedidosUrl = linkTo(ProdutoServicoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), rel);


    }

    public Link linkToAtivacaoProdutoServico(String produtoServicoId, String rel) {
        return linkTo(methodOn(ProdutoServicoController.class)
                .ativar(produtoServicoId)).withRel(rel);
    }

    public Link linkToInativacaoProdutoServico(String produtoServicoId, String rel) {
        return linkTo(methodOn(ProdutoServicoController.class)
                .inativar(produtoServicoId)).withRel(rel);
    }

    //Produto
    public Link linkToProduto(UUID produtoId, String rel) {
        return linkTo(methodOn(ProdutoController.class)
                .buscar(produtoId)).withRel(rel);
    }

    public Link linkToProduto(UUID produtoId) {
        return linkToProduto(produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProdutos(String rel) {
        return linkTo(ProdutoController.class).withRel(rel);
    }

    public Link linkToProdutos() {
        return linkToProdutos(IanaLinkRelations.SELF.value());
    }

    //Servico
    public Link linkToServico(UUID servicoId, String rel) {
        return linkTo(methodOn(ServicoController.class)
                .buscar(servicoId)).withRel(rel);
    }

    public Link linkToServico(UUID servicoId) {
        return linkToServico(servicoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToServicos(String rel) {
        return linkTo(ServicoController.class).withRel(rel);
    }

    public Link linkToServicos() {
        return linkToServicos(IanaLinkRelations.SELF.value());
    }



}
