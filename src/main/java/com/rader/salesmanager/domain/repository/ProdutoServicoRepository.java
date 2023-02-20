package com.rader.salesmanager.domain.repository;

import com.rader.salesmanager.domain.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>,
        JpaSpecificationExecutor<ProdutoServico> {

    Optional<ProdutoServico> findByIdAndProduto(UUID id, boolean isProduto);

    Optional<ProdutoServico> findById(UUID id);

}
