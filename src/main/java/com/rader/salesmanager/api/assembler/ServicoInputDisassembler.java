package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.model.input.ProdutoInput;
import com.rader.salesmanager.api.model.input.ServicoInput;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoServico toDomainObject(ServicoInput servicoInput) {
        return modelMapper.map(servicoInput, ProdutoServico.class);
    }

    public void copyToDomainObject(ServicoInput servicoInput, ProdutoServico servico) {
        modelMapper.map(servicoInput, servico);
    }
}
