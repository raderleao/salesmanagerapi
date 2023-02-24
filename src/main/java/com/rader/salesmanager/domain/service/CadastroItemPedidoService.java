package com.rader.salesmanager.domain.service;

import com.rader.salesmanager.domain.exception.ProdutoServicoNaoEncontradoException;
import com.rader.salesmanager.domain.model.ItemPedido;
import com.rader.salesmanager.domain.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CadastroItemPedidoService {
    private static final String MSG_PRODUTO_SERVICO_EM_USO
            = "Item Produto de id %s não pode ser removido, pois está em uso";

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public ItemPedido buscar(UUID id) {
       return buscarOuFalhar(id);
    }


    public ItemPedido buscarOuFalhar(UUID id) {
        return itemPedidoRepository.findById(id)
                .orElseThrow(() -> new ProdutoServicoNaoEncontradoException(id.toString()));
    }
}
