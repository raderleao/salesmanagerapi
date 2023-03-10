package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class ProdutoServicoModel extends RepresentationModel<ProdutoServicoModel> {

    @ApiModelProperty(example = "d427f5cf-b3fb-4ea4-bd8f-2f883ff0c4dd")
    private UUID id;

    @ApiModelProperty(example = "Troca de óleo")
    private String nome;

    @ApiModelProperty(example = "Troca de óleo de motor")
    private String descricao;

    @ApiModelProperty(example = "30.34")
    private BigDecimal preco;

    @ApiModelProperty(example = "True")
    private Boolean ativo;
}
