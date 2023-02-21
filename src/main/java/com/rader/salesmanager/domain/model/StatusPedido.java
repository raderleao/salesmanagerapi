package com.rader.salesmanager.domain.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum StatusPedido {
    ABERTO("Aberto"),
    FECHADO("Fechado", ABERTO),
    CANCELADO("Cancelado", Arrays.asList(ABERTO, FECHADO));

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {

        return !novoStatus.statusAnteriores.contains(this);
    }

    public boolean podeAlterarPara(StatusPedido status) {
        return !naoPodeAlterarPara(status);
    }

}
