package com.inv.op.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.inv.op.backend.service.SaleModuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/saleModule")
public class SaleModuleController {
    
    @Autowired
    SaleModuleService saleModuleService;

    @GetMapping(path = "/sale")
    public String getSaleList(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }


    @GetMapping(path = "/sale/{id}")
    public String getSale(@RequestBody String requestBody, @PathVariable String id){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }


    @PostMapping(path = "/sale")
    public String saveSale(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    


}
