package com.rader.salesmanager.domain.repository;

import com.rader.salesmanager.domain.model.ProdutoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, UUID>,
        JpaSpecificationExecutor<ProdutoServico> {

    Optional<ProdutoServico> findByIdAndProduto(UUID id, boolean isProduto);

    @Query("from ProdutoServico ps where ps.id = :id")
    Optional<ProdutoServico> findById(@Param("id") UUID id);

}
