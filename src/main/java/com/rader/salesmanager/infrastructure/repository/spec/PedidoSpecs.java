package com.rader.salesmanager.infrastructure.repository.spec;

import com.rader.salesmanager.domain.filter.PedidoFilter;
import com.rader.salesmanager.domain.model.Pedido;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

@Component
public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            if (filtro.getStatus() != null) {
                predicates.add(builder.
                        equal(root.get("status"), filtro.getStatus()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}