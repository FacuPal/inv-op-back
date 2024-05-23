package com.inv.op.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.dto.ProductResponseDto;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;

    @GetMapping(path = "/{id}", produces = "application/json")
    public  ProductResponseDto  getProducts(@PathVariable Long id){
        return productService.getProducts(id);      
    }

    @PostMapping
    public Optional<Product> saveProduct(@RequestBody Product product){
        return Optional.empty();
    }
}
