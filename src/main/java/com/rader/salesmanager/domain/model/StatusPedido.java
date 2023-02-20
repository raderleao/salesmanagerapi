package com.rader.salesmanager.domain.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedido {
    ABERTO("Aberto"),
    FECHADO("Fechado");

    private final String descricao;
}
