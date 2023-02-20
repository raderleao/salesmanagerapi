package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.model.input.ProdutoInput;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoServico toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, ProdutoServico.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, ProdutoServico produto) {
        modelMapper.map(produtoInput, produto);
    }
}
