package com.inv.op.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.service.PurchaseOrderModuleService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/purchasOrderModule")
public class PurchaseOrderModuleController {

    @Autowired
    PurchaseOrderModuleService purchaseOrderModuleService;
    
    @GetMapping(path = "/purchaseOrder")
    public ResponseEntity<?> getPurchaseOrderList() {
        return ResponseEntity.ok(purchaseOrderModuleService.getPurchaseOrderList());
    }

    @GetMapping(path = "/purchaseOrder/{purchaseOrderId}")
    public ResponseEntity<?> getPurchaseOrder(@PathVariable Long purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderModuleService.getPurchaseOrder(purchaseOrderId));
    }

}
