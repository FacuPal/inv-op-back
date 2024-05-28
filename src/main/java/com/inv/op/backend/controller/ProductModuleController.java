package com.inv.op.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.dto.ProductResponseDto;
import com.inv.op.backend.dto.SupplierResponseDto;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.service.ProductModuleService;

@RestController
@RequestMapping("/productModule")
public class ProductModuleController {
    
    @Autowired
    ProductModuleService productModuleService;

    //Product endpoints
    @GetMapping(path = "/product/{id}", produces = "application/json")
    public  ProductResponseDto  getProduct(@PathVariable Long id){
        return productModuleService.getProduct(id);      
    }


    // @PostMapping(path = "/product")
    // @ResponseStatus(code = HttpStatus.CREATED)
    // public ProductResponseDto saveProduct(@RequestBody Product product){
    //     return productModuleService.saveProduct(product);
    // }


    //Supplier Endpoints
    @GetMapping(path = "/supplier/{id}", produces = "application/json")
    public SupplierResponseDto getSupplier(@PathVariable Long id){
        return productModuleService.getSuppliers(id);
    }

    
}
