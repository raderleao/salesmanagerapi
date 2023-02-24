package com.rader.salesmanager.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Setter
@Getter
public class ProdutoServicoResumoModel extends RepresentationModel<ProdutoServicoResumoModel> {

    @ApiModelProperty(example = "d427f5cf-b3fb-4ea4-bd8f-2f883ff0c4dd")
    private UUID id;

    @ApiModelProperty(example = "Bicleta")
    private String nome;

    @ApiModelProperty(example = "True")
    private Boolean produto;

}
