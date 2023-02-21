package com.rader.salesmanager.testes.produtoservico;

import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.service.CadastroProdutoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestExcluirProdutoServico {

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Test
    public void testExcluirProdutoServico() {
        ProdutoServico produtoServico = new ProdutoServico();
        produtoServico.setDescricao("Produto Teste");
        produtoServico.setPreco(new BigDecimal("20.00"));
        produtoServico.setProduto(true);
        ProdutoServico produtoSalvo = cadastroProduto.salvar(produtoServico);

        cadastroProduto.excluir(produtoSalvo.getId());

        assertThrows(EntidadeNaoEncontradaException.class, () -> cadastroProduto.buscarOuFalhar(produtoSalvo.getId()));
    }

}
