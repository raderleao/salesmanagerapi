package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.ServicoNaoEncontradoException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.rader.salesmanager.infrastructure.repository.spec.ProdutoServicoSpecs.doTipoServico;

@Service
public class CadastroServicoService {
    private static final String MSG_SERVICO_EM_USO
            = "Servico de id %s não pode ser removido, pois está em uso";

    @Autowired
    private ProdutoServicoRepository psRepository;

    @Transactional
    public ProdutoServico salvar(ProdutoServico ps) {
        ps.setProduto(false);
        return psRepository.save(ps);

    }

    public Page<ProdutoServico> buscarTodos(
            Specification<ProdutoServico> filtro, Pageable pageable) {

            return psRepository.findAll(doTipoServico().and(filtro), pageable);
    }

    public ProdutoServico buscarOuFalhar(UUID id) {
            return psRepository.findByIdAndProduto(id, false)
                    .orElseThrow(() -> new ServicoNaoEncontradoException(id));
    }
}