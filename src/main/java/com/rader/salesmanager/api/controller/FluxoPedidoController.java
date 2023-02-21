package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.model.input.PedidoDescontoInput;
import com.rader.salesmanager.domain.service.EmissaoPedidoService;
import com.rader.salesmanager.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/pedidos/{id}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService fluxoPedido;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @PutMapping(path = "/desconto", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> descontar(@PathVariable String id,
                                            @RequestBody @Valid PedidoDescontoInput descontoInput){
        var uuIdPedido = UUID.fromString(id);

        var pctDesconto = descontoInput.getDesconto();
        fluxoPedido.darDesconto(uuIdPedido,pctDesconto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/fechamento", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable String id ){

        var uuIdPedido = UUID.fromString(id);
        fluxoPedido.fechar(uuIdPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/cancelamento", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable String id ){

        var uuIdPedido = UUID.fromString(id);
        fluxoPedido.cancelar(uuIdPedido);

        return ResponseEntity.noContent().build();
    }
}
