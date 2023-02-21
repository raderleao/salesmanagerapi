package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.EntidadeEmUsoException;
import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CadastroProdutoServicoService {
    private static final String MSG_PRODUTO_SERVICO_EM_USO
            = "Produto ou Serviço de id %s não pode ser removido, pois está em uso";
    @Autowired
    private ProdutoServicoRepository psRepository;

    @Transactional
    public void ativar(String id){
         var produtoServico = buscarOuFalhar(UUID.fromString(id));
         produtoServico.ativar();
         psRepository.save(produtoServico);
    }

    @Transactional
    public void ativar(List<String> ids) {
        ids.forEach(this::ativar);
    }

    @Transactional
    public void inativar(String id){
        var produtoServico = buscarOuFalhar(UUID.fromString(id));
        produtoServico.inativar();
        psRepository.save(produtoServico);
    }

    @Transactional
    public void excluir(UUID id) {
       ProdutoServico produtoServico = buscarOuFalhar(id);
        try {
            psRepository.delete(produtoServico);
            psRepository.flush();

        } catch (Exception e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_SERVICO_EM_USO, id));
        }
    }

    public Page<ProdutoServico> buscarTodos(
            Specification<ProdutoServico> filtro, Pageable pageable) {

        return psRepository.findAll(filtro, pageable);
    }

    public ProdutoServico buscarOuFalhar(UUID id) {

        return psRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(id.toString()));
    }
}

