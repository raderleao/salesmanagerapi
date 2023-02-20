package com.rader.salesmanager.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class PedidoInput {

    @Valid
    @NotNull
    private List<ItemPedidoInput> itens;

    @Data
    public static class ItemPedidoInput {

        @ApiModelProperty(example = "ad084f43-7d41-4735-b207-7e3f09c533f2", required = true)
        @Valid
        private UUID id;

        @NotNull
        private Integer quantidade;

    }


}
