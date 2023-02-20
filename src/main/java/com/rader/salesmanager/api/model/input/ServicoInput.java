package com.rader.salesmanager.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;


@Setter
@Getter
public class ServicoInput {

    @ApiModelProperty(example = "Troca de óleo", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Troca de óleo do motor", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "90.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

}
