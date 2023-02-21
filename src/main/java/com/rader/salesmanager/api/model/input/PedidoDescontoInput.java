package com.rader.salesmanager.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Setter
@Getter
public class PedidoDescontoInput {

    @Valid
    @ApiModelProperty(example = "Percentual de desconto", required = true)
    @NotNull
    private BigDecimal desconto;

}
