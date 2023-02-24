package com.rader.salesmanager.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private UUID id;

    @ApiModelProperty(example = "ABERTO")
    private String status;

    private List<ItemPedidoResumoModel> produtos;

    private List<ItemPedidoResumoModel> servicos;

    @ApiModelProperty(example = "245.65")
    private BigDecimal valorTotalServicos;

    @ApiModelProperty(example = "245.65")
    private BigDecimal valorTotalProdutos;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "37.90")
    private BigDecimal desconto;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @JsonIgnore
    private List<ItemPedidoResumoModel> itens;

}
