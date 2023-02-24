package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.ProdutoServicoController;
import com.rader.salesmanager.api.model.ProdutoServicoModel;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoServicoModelAssembler
        extends RepresentationModelAssemblerSupport<ProdutoServico, ProdutoServicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesLinks salesLinks;

    public ProdutoServicoModelAssembler() {

        super(ProdutoServicoController.class, ProdutoServicoModel.class);
    }

    @Override
    public ProdutoServicoModel toModel(ProdutoServico ps) {
        ProdutoServicoModel psModel
                = createModelWithId(ps.getId(), ps);

        modelMapper.map(ps, psModel);

        psModel.add(salesLinks.linkToProdutosServicos("produtos-servicos"));
        if (!ps.getAtivo()) {
            psModel.add(salesLinks.linkToAtivacaoProdutoServico(ps.getId().toString(), "ativar"));
        }
        if(ps.getAtivo()){
            psModel.add(salesLinks.linkToInativacaoProdutoServico(ps.getId().toString(), "desativar"));
        }
        return psModel;
    }













}
