package com.inv.op.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.inv.op.backend.dto.DTOError;
import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.service.SaleModuleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @CrossOrigin(originPatterns = "*")
    @GetMapping(path = "/sale")
    public  ResponseEntity<?> getSaleList() throws HttpServerErrorException {
        return ResponseEntity.ok(saleModuleService.getSaleList());
    }

    // @CrossOrigin(originPatterns = "*")
    @GetMapping(path = "/sale/{id}")
    public ResponseEntity<?> getSale(@PathVariable Long id){
        return ResponseEntity.ok(saleModuleService.getSale(id));
    }

    // @CrossOrigin(originPatterns = "*")
    @PutMapping(path = "/sale/{id}")
    public SaleDto updateSale(@PathVariable Long id, @RequestBody SaleDto requestBody){
        return saleModuleService.updateSale(id, requestBody);
    }
    
    // @CrossOrigin(originPatterns = "*")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/sale")
    public ResponseEntity<?> saveNewSale(@RequestBody SaleDto requestBody){
        return ResponseEntity.ok(saleModuleService.saveNewSale(requestBody));
    }    


}
