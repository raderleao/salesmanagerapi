package com.rader.salesmanager.core.converter;

import com.rader.salesmanager.domain.exception.EntidadeNaoEncontradaException;
import com.rader.salesmanager.domain.model.ProdutoServico;
import com.rader.salesmanager.domain.repository.ProdutoServicoRepository;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToProdutoServicoConverter implements Converter<String, ProdutoServico> {

    @Autowired
    private ProdutoServicoRepository produtoServicoRepository;

    @Override
    public ProdutoServico convert(MappingContext<String, ProdutoServico> context) {
        String source = context.getSource();
        if (source == null || source.isEmpty()) {
            return null;
        }
        UUID id = UUID.fromString(source);
        return produtoServicoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe um produto/serviço com o ID %s", source)));
    }
}

