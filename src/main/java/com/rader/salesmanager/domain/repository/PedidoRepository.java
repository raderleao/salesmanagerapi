package com.rader.salesmanager.domain.repository;

import com.rader.salesmanager.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID>,
        JpaSpecificationExecutor<Pedido> {
}