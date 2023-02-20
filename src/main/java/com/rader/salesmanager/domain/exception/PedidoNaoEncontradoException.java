package com.rader.salesmanager.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public PedidoNaoEncontradoException(String id) {
        super(String.format("Não existe um pedido com código %s", id));
    }
}
