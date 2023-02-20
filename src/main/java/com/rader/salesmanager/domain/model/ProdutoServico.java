package com.rader.salesmanager.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class ProdutoServico {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    private String codigo;

    private Boolean produto = Boolean.FALSE;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    private BigDecimal preco;

    private Boolean ativo = Boolean.TRUE;

    @OneToMany(mappedBy = "produtoServico")
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    @PreRemove
    private void verificarSePodeExcluir() {
        if (!itensPedidos.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um produto ou serviço associado a um pedido.");
        }
    }

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }
    @PrePersist
    private void gerarCodigo() {

        setCodigo(UUID.randomUUID().toString());
    }


}