package com.rader.salesmanager.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoFilter {

    @ApiModelProperty(example = "True", value = "Booleano se ativo para filtro da pesquisa")
    private Boolean ativo;

    @ApiModelProperty(example = "Óleo de motor", value = "Nome do produto para filtro da pesquisa")
    private String nome;

    @ApiModelProperty(example = "10.90", value = "Preço inicial do produto para filtro da pesquisa")
    private BigDecimal precoInicial;

    @ApiModelProperty(example = "23.90", value = "Preço final do produto para filtro da pesquisa")
    private BigDecimal precoFinal;

}
