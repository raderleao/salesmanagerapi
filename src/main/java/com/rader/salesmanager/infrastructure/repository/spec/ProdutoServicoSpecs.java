package com.rader.salesmanager.infrastructure.repository.spec;

import com.rader.salesmanager.domain.filter.ProdutoFilter;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.text.Normalizer;
import java.util.ArrayList;

@Component
public class ProdutoServicoSpecs {

    public static Specification<ProdutoServico> usandoFiltro(ProdutoFilter filtro) {
        return (root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            if (filtro.getAtivo() != null) {
                predicates.add(builder.
                        equal(root.get("ativo"), filtro.getAtivo()));
            }

            if (filtro.getPrecoInicial() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("preco"),
                        filtro.getPrecoInicial()));
            }

            if (filtro.getPrecoFinal() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("preco"),
                        filtro.getPrecoFinal()));
            }

            if (filtro.getNome() != null) {
                String nomeFormatado = Normalizer.normalize(filtro.getNome(), Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                        .toLowerCase();
                predicates.add(builder.like(
                        builder.lower(root.get("nome")),
                        "%" + nomeFormatado + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ProdutoServico> doTipoProduto() {
        return (root, query, builder) ->
                builder.equal(root.get("produto"), true);
    }

    public static Specification<ProdutoServico> doTipoServico() {
        return (root, query, builder) ->
                builder.equal(root.get("produto"), false);
    }

}