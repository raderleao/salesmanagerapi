package com.rader.salesmanager.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoFilter {

    @ApiModelProperty(example = "True", value = "Status do produto para filtro da pesquisa")
    private Boolean status;

}
