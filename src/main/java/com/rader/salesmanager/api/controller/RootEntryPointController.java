package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.SalesLinks;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private SalesLinks salesLinks;

    @GetMapping
    @Operation(hidden = true)
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();

            rootEntryPointModel.add(salesLinks.linkToPedidos("pedidos"));
            rootEntryPointModel.add(salesLinks.linkToProdutosServicos("produtos-servicos"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }

}
