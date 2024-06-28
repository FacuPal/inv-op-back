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
import java.util.Map;
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
    public ResponseEntity<?> getMissingProducts() {
        try {
            return ResponseEntity.ok(inventoryModuleService.getMissingProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }

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
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return inventoryModuleService.getProductById(id);
    }

    @GetMapping("/{id}/family")
    public ProductFamily getProductFamily(@PathVariable Long id) {
        return inventoryModuleService.getProductById(id).getProductFamily();
    }


    @GetMapping("/product/{id}/inventoryModel")
    public ResponseEntity<?> getInventoryModelForProduct(@PathVariable Long id) {
        try {
            String inventoryModel = inventoryModuleService.getInventoryModelForProduct(id);
            return ResponseEntity.ok(inventoryModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping("/product/{id}/calculateCGI")
    public ResponseEntity<?> calculateCGIForProduct(@PathVariable Long id) {
        try {
            double cgi = inventoryModuleService.calculateCGI(id);
            return ResponseEntity.ok(cgi);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }
    /*
    @GetMapping("/calculateFixedLot/{productId}")
    public ResponseEntity<?> calculateFixedLotForProduct(@PathVariable Long productId) {
        try {
            Map<String, Double> calculations = inventoryModuleService.calculateFixedLotForProduct(productId);
            return ResponseEntity.ok(calculations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }*/
/*
    @GetMapping("/calculateFixedInterval/{productId}")
    public ResponseEntity<?> calculateFixedIntervalForProduct(@PathVariable Long productId) {
        try {
            double safetyStock = inventoryModuleService.calculateFixedIntervalForProduct(productId);
            return ResponseEntity.ok(safetyStock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }
*/
    @GetMapping("/optimalBatch/{productId}")
    public ResponseEntity<?> calculateOptimalBatch(@PathVariable Long productId) {
        try {
            Product product = inventoryModuleService.getProductById(productId);
            double optimalBatch = product.calculateOptimalBatch();
            return ResponseEntity.ok(Map.of("optimalBatch", optimalBatch));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping("/orderLimit/{productId}")
    public ResponseEntity<?> calculateOrderLimit(@PathVariable Long productId) {
        try {
            Product product = inventoryModuleService.getProductById(productId);
            double orderLimit = product.calculateOrderLimit();
            return ResponseEntity.ok(Map.of("orderLimit", orderLimit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping("/safetyStock/{productId}")
    public ResponseEntity<?> calculateSafetyStock(@PathVariable Long productId) {
        try {
            Product product = inventoryModuleService.getProductById(productId);
            double safetyStock = product.calculateSafetyStock();
            return ResponseEntity.ok(Map.of("safetyStock", safetyStock));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }


    @GetMapping("/productData/{productId}")
    public ResponseEntity<?> getProductData(@PathVariable Long productId) {
        try {
            return ResponseEntity.ok(inventoryModuleService.getProductData(productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }
}
