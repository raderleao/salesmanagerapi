package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class ProdutoServicoModel extends RepresentationModel<ProdutoServicoModel> {

    private UUID id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;
}
