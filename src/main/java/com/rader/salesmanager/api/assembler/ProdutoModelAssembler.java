package com.rader.salesmanager.api.assembler;

import com.rader.salesmanager.api.SalesLinks;
import com.rader.salesmanager.api.controller.ProdutoController;
import com.rader.salesmanager.domain.model.ProdutoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rader.salesmanager.api.model.ProdutoModel;

@Component
public class ProdutoModelAssembler
        extends RepresentationModelAssemblerSupport<ProdutoServico, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SalesLinks salesLinks;

    public ProdutoModelAssembler() {

        super(ProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(ProdutoServico produto) {
        ProdutoModel produtoModel
                = createModelWithId(produto.getId(), produto);

        modelMapper.map(produto, produtoModel);
        produtoModel.add(salesLinks.linkToProdutosServicos("produtos"));

        if (!produto.getAtivo()) {
            produtoModel.add(salesLinks.linkToAtivacaoProdutoServico(produtoModel.getId().toString(), "ativar"));
        }
        if(produto.getAtivo()){
            produtoModel.add(salesLinks.linkToInativacaoProdutoServico(produtoModel.getId().toString(), "desativar"));
        }
        return produtoModel;
    }
    @Override
    public CollectionModel<ProdutoModel> toCollectionModel(Iterable<? extends ProdutoServico> entities) {
        CollectionModel<ProdutoModel> collectionModel = super.toCollectionModel(entities);

            collectionModel.add(salesLinks.linkToProdutos());

        return collectionModel;
    }
}
