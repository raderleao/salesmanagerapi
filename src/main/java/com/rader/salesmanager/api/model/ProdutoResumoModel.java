package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(collectionRelation = "produtos")
@Setter
@Getter
public class ProdutoResumoModel extends RepresentationModel<ProdutoResumoModel> {

    @ApiModelProperty(example = "1")
    private UUID id;

    @ApiModelProperty(example = "true")
    private Boolean isProduto;

    @ApiModelProperty(example = "Oléo para carro")
    private String nome;

    @ApiModelProperty(example = "true")
    private Boolean ativo;
}
