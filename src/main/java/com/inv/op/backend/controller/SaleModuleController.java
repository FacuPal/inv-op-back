package com.inv.op.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.service.SaleModuleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;



@RestController
@RequestMapping("/saleModule")
public class SaleModuleController {
    
    @Autowired
    SaleModuleService saleModuleService;

    @CrossOrigin("*")
    @GetMapping(path = "/sale")
    public  List<SaleDto> getSaleList() throws HttpServerErrorException {
        return saleModuleService.getSaleList();
    }

    @GetMapping(path = "/sale/{id}")
    public SaleDto getSale(@PathVariable Long id){
        return saleModuleService.getSale(id);
    }

    @PutMapping(path = "/sale/{id}")
    public SaleDto updateSale(@PathVariable Long id, @RequestBody SaleDto requestBody){
        return saleModuleService.updateSale(id, requestBody);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/sale")
    public SaleDto saveNewSale(@RequestBody SaleDto requestBody){
        return saleModuleService.saveNewSale(requestBody);
    }    


}
