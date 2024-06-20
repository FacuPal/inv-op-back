package com.inv.op.backend.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;
import java.util.Optional;
import java.util.Collection;

import com.inv.op.backend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping(path = "/product")
    public ResponseEntity<?> getProductList() throws ProductNotFoundError {
        return ResponseEntity.ok(productModuleService.getProductList());
    }

    @GetMapping(path = "/product/{id}", produces = "application/json")
    public Optional<ProductDto> getProduct(@PathVariable Long id) throws ProductNotFoundError {
        return productModuleService.getProduct(id);
    }
    @GetMapping(path = "/product/{id}/dto", produces = "application/json")
    public ResponseEntity<DTOProductoLista> getDTOProductoLista(@PathVariable Long id) {
        DTOProductoLista dtoProducto = productModuleService.getDTOProductoLista(id);
        return ResponseEntity.ok(dtoProducto);
    }

    @PostMapping(path = "/product")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> saveProduct(@RequestBody CreateProductRequest product) throws ProductSaveError {
        return ResponseEntity.ok().body(productModuleService.saveProduct(product));
    }

    @GetMapping(path = "/product/{id}/defaultSupplier")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> getDefaultSupplier(@PathVariable Long id) throws RuntimeException {
        return ResponseEntity.ok().body(productModuleService.getDefaultSupplier(id));
    }


    // Supplier Endpoints
    @GetMapping(path = "/supplier/{id}", produces = "application/json")
    public SupplierDto getSupplier(@PathVariable Long id) throws SupplierNotFoundError {
        return productModuleService.getSupplier(id);
    }

    @GetMapping(path = "/supplier", produces = "application/json")
    public ResponseEntity<?> getSupplierList(){
        return ResponseEntity.ok().body(productModuleService.getSupplierList());
    }

    @GetMapping(path = "/product", produces = "application/json")
    public ResponseEntity<List<DTOProductoLista>> getAllProducts() {
        List<DTOProductoLista> products = productModuleService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @PutMapping(path = "/product/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Serializable> updateProduct(@PathVariable Long id, @RequestBody CreateProductRequest updatedProduct) throws ProductNotFoundError, ProductSaveError {
        productModuleService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = "/productFamilies", produces = "application/json")
    public ResponseEntity<List<ProductoFamiliaDto>> getAllProductFamilies() {
        List<ProductoFamiliaDto> productFamilies = productModuleService.getAllProductFamilies();
        return ResponseEntity.ok(productFamilies);
    }
    @DeleteMapping(path = "/product/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productModuleService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/product/{id}/restore")
    public ResponseEntity<String> restoreProduct(@PathVariable Long id) {
        productModuleService.restoreProduct(id);
        return ResponseEntity.ok("Product restored successfully");
    }


}
