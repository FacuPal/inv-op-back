package com.inv.op.backend.controller;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.dto.CreateProductRequest;
import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.dto.SupplierDto;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductSaveError;
import com.inv.op.backend.error.supplier.SupplierNotFoundError;
import com.inv.op.backend.service.ProductModuleService;

@RestController
@RequestMapping("/productModule")
public class ProductModuleController {

    @Autowired
    ProductModuleService productModuleService;

    // Product endpoints
    @GetMapping(path = "/product/{id}", produces = "application/json")
    public Optional<ProductDto> getProduct(@PathVariable Long id) throws ProductNotFoundError {
        return productModuleService.getProduct(id);
    }

    @PostMapping(path = "/product")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Serializable> saveProduct(@RequestBody CreateProductRequest product) throws ProductSaveError {
        return ResponseEntity.ok().body(productModuleService.saveProduct(product));
    }

    // Supplier Endpoints
    @GetMapping(path = "/supplier/{id}", produces = "application/json")
    public SupplierDto getSupplier(@PathVariable Long id) throws SupplierNotFoundError {
        return productModuleService.getSupplier(id);
    }

}
