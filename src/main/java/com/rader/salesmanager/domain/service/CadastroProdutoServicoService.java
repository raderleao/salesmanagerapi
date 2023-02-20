package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CadastroProdutoServicoService {

    @Autowired
    private ProdutoServicoRepository psRepository;


    public Page<ProdutoServico> buscarTodos(
            Specification<ProdutoServico> filtro, Pageable pageable) {

        return psRepository.findAll(filtro, pageable);
    }

    public ProdutoServico buscarOuFalhar(UUID id) {
        return psRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(id.toString()));
    }
}