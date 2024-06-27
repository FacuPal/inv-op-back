package com.inv.op.backend.controller;

import com.inv.op.backend.dto.DTOError;
import com.inv.op.backend.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inv.op.backend.service.InventoryModuleService;
import org.springframework.web.client.HttpServerErrorException;

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

    @GetMapping(path = "/missingProduct")
    public ResponseEntity<?> getMissingProducts(){
        try {
            return ResponseEntity.ok(inventoryModuleService.getMissingProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
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
