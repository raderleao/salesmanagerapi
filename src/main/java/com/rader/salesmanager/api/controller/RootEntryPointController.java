package com.rader.salesmanager.api.controller;

import com.rader.salesmanager.api.SalesLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.rader.salesmanager.api.AlgaLinks;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private SalesLinks salesLinks;

    @GetMapping
    public RootEntryPointModel root() {
        var rootEntryPointModel = new RootEntryPointModel();


        rootEntryPointModel.add(salesLinks.linkToPedidos("pedidos"));

        return rootEntryPointModel;

    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
    }
}
