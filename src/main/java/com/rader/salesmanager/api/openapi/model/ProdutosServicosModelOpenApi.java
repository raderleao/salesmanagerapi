package com.rader.salesmanager.api.openapi.model;

import com.rader.salesmanager.api.model.ProdutoServicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProdutosModel")
@Data
public class ProdutosServicosModelOpenApi {

    private ProdutosServicosModelOpenApi _embedded;
    private Links _links;

    @ApiModel("ProdutosServicosEmbeddedModel")
    @Data
    public class ProdutosEmbeddedModelOpenApi {

        private List<ProdutoServicoModel> produtosservicos;

    }
}
