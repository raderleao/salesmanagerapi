package com.rader.salesmanager.domain.exception;

import java.util.UUID;

public class ProdutoServicoNaoAtivoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoServicoNaoAtivoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoServicoNaoAtivoException(UUID id) {
        this(String.format("Produto ou Serviço de id %s não está ativo.",
                id.toString()));
    }
}