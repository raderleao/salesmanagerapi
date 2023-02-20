package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoServicoService {

    @Autowired
    private ProdutoServicoRepository psRepository;

    public ProdutoServico buscarOuFalhar(UUID id) {
        return psRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(id.toString()));

    }
}