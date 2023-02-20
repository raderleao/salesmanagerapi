package com.rader.salesmanager.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ItemPedidoResumoInput {
    @NotNull
    private UUID id;

    @NotNull
    private Integer quantidade;
}
