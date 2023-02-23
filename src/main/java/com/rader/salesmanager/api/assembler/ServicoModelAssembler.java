package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.ServicoController;
import com.rader.salesmanager.api.model.ServicoModel;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ServicoModelAssembler
        extends RepresentationModelAssemblerSupport<ProdutoServico, ServicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesLinks salesLinks;

    public ServicoModelAssembler() {

        super(ServicoController.class, ServicoModel.class);
    }

    @Override
    public ServicoModel toModel(ProdutoServico servico) {
        ServicoModel servicoModel
                = createModelWithId(servico.getId(), servico);

        modelMapper.map(servico, servicoModel);

        servicoModel.add(salesLinks
                .linkToProdutosServicos(servico.getId().toString()));
        return servicoModel;
    }













}