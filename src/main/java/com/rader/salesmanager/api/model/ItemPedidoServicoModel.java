package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ItemPedidoServicoModel extends RepresentationModel<ItemPedidoServicoModel> {

    @ApiModelProperty(example = "d427f5cf-b3fb-4ea4-bd8f-2f883ff0c4dd")
    private UUID id;

    private ServicoResumoModel servico;

    @ApiModelProperty(example = "2")
    private Integer quantidade;

    @ApiModelProperty(example = "78.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "157.80")
    private BigDecimal precoTotal;

}
