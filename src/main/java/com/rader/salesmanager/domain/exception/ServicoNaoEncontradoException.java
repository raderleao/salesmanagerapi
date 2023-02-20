package com.rader.salesmanager.domain.exception;

import java.util.UUID;

public class ServicoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ServicoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ServicoNaoEncontradoException(UUID id) {
        this(String.format("Não existe um cadastro de servico com id %s.",
                id.toString()));
    }
}