package com.rader.salesmanager.domain.exception;

import java.util.UUID;

public class ItemPedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ItemPedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ItemPedidoNaoEncontradoException(UUID id) {
        this(String.format("Não existe um cadastro de ItemPedido com id %s.",
                id.toString()));
    }
}