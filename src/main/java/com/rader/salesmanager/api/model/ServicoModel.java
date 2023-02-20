package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.UUID;

@Relation(collectionRelation = "servicos")
@Setter
@Getter
public class ServicoModel extends RepresentationModel<ServicoModel> {

    @ApiModelProperty(example = "1")
    private UUID id;

    @ApiModelProperty(example = "Troca de Óleo")
    private String nome;

    @ApiModelProperty(example = "Troca de óleo em carros")
    private String descricao;

    @ApiModelProperty(example = "90.50")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private Boolean ativo;
}
