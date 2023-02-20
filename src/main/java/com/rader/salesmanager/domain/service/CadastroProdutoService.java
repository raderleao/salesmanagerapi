package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.EntidadeEmUsoException;
import com.rader.salesmanager.domain.exception.ProdutoNaoEncontradoException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.rader.salesmanager.infrastructure.repository.spec.ProdutoServicoSpecs.doTipoProduto;

@Service
public class CadastroProdutoService {
    private static final String MSG_PRODUTO_EM_USO
            = "Produto de id %s não pode ser removido, pois está em uso";

    @Autowired
    private ProdutoServicoRepository psRepository;

    @Transactional
    public ProdutoServico salvar(ProdutoServico ps) {
        ps.setProduto(true);
        return psRepository.save(ps);

    }

    @Transactional
    public void excluir(UUID id) {
        ProdutoServico produto = buscarOuFalhar(id);
        try {
            psRepository.delete(produto);
            psRepository.flush();

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, id));
        }
    }

    @Transactional
    public void ativar(UUID id) {
        ProdutoServico produto = buscarOuFalhar(id);
        produto.ativar();
    }

    @Transactional
    public void inativar(UUID id) {
        ProdutoServico produto = buscarOuFalhar(id);
        produto.ativar();
    }

    public Page<ProdutoServico> buscarTodos(
            Specification<ProdutoServico> filtro, Pageable pageable) {

        return psRepository.findAll(doTipoProduto().and(filtro), pageable);
    }

    public ProdutoServico buscarOuFalhar(UUID id) {
        return psRepository.findByIdAndProduto(id, true)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

    }
}