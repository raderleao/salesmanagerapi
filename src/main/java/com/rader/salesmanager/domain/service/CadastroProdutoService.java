package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.ProdutoNaoEncontradoException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CadastroProdutoService {

    @Autowired
    private ProdutoServicoRepository psRepository;

    @Transactional
    public ProdutoServico salvar(ProdutoServico ps) {
        ps.setProduto(true);
        return psRepository.save(ps);

    }

    public ProdutoServico buscarOuFalhar(UUID id) {
        return psRepository.findByIdAndProduto(id, true)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

    }
}