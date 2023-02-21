package com.rader.salesmanager.domain.exception;

public class ProdutoServicoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoServicoNaoEncontradoException(String id) {
        super(String.format("Não existe um produto ou serviço com código %s", id));
    }
}
