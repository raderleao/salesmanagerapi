package com.rader.salesmanager.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ServicoFilter {

    @ApiModelProperty(example = "True", value = "Booleano se ativo para filtro da pesquisa")
    private Boolean ativo;

    @ApiModelProperty(example = "Troca de Óleo", value = "Nome do serviço para filtro da pesquisa")
    private Long nome;

    @ApiModelProperty(example = "7.54", value = "Preço inicial do serviço para filtro da pesquisa")
    private BigDecimal precoInicial;

    @ApiModelProperty(example = "53.45", value = "Preço final do serviço para filtro da pesquisa")
    private BigDecimal precoFinal;

}
