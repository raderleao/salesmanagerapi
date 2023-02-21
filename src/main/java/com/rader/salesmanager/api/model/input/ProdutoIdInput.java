package com.rader.salesmanager.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Setter
@Getter
public class ProdutoIdInput {

    @ApiModelProperty(example = "ID de produto", required = true)
    @NotBlank
    private UUID id;

}
