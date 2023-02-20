package com.rader.salesmanager.domain.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(UUID id) {
        this(String.format("Não existe um cadastro de produto com id %s.",
                id.toString()));
    }
}