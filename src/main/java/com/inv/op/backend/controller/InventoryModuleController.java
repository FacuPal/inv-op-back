package com.inv.op.backend.controller;

import com.inv.op.backend.dto.DTOError;
import com.inv.op.backend.dto.DTOMissingProduct;
import com.inv.op.backend.dto.ProductoFamiliaDto;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.service.ProductModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inv.op.backend.service.InventoryModuleService;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventoryModule")
public class InventoryModuleController {
    
    @Autowired
    InventoryModuleService inventoryModuleService;

    @GetMapping(path = "/restockProduct")
    public ResponseEntity<?> getRestockProducts(){
        try {
            return ResponseEntity.ok(inventoryModuleService.getRestockProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping("/missingProducts")
    public List<DTOMissingProduct> getMissingProducts() {
        List<Product> missingProducts = inventoryModuleService.getMissingProducts();
        return missingProducts.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private DTOMissingProduct toResponse(Product product) {
        int missingAmount = (int) Math.ceil(product.calculateSafetyStock()) - product.getStock();
        return new DTOMissingProduct(
                product.getProductId(),
                product.getProductName(),
                product.getStock(),
                missingAmount
        );
    }


    @GetMapping("/calculate/{productId}")
    public ResponseEntity<?> calculateInventoryForProduct(@PathVariable Long productId) {
        try {
            Product product = inventoryModuleService.calculateInventoryForProduct(productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

}
