package com.inv.op.backend.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.service.PurchaseOrderModuleService;

@RestController
@RequestMapping("/purchaseOrderModule")
public class PurchaseOrderModuleController {

    @Autowired
    PurchaseOrderModuleService purchaseOrderModuleService;
    
    @GetMapping(path = "/purchaseOrder")
    public ResponseEntity<?> getPurchaseOrderList() {
        return ResponseEntity.ok(purchaseOrderModuleService.getPurchaseOrderList());
    }

    @PostMapping(path = "/purchaseOrder")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderDto newPurchaseOrder ) {
        return ResponseEntity.ok(purchaseOrderModuleService.createPurchaseOrder(newPurchaseOrder));
    }

    @GetMapping(path = "/purchaseOrder/{purchaseOrderId}")
    public ResponseEntity<?> getPurchaseOrder(@PathVariable Long purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderModuleService.getPurchaseOrder(purchaseOrderId));
    }

    @PostMapping(path = "/purchaseOrder/{purchaseOrderId}/close")
    public ResponseEntity<?> closePurchaseOrder(@PathVariable Long purchaseOrderId) {
        return ResponseEntity.ok(purchaseOrderModuleService.closePurchaseOrder(purchaseOrderId));
    }

    @GetMapping(path = "/fixedIntervalPurchaseOrder")
    public ResponseEntity<?> getFixedIntervalPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderModuleService.getFixedIntervalPurchaseOrders());
    }

    @PostMapping(path = "/batchCreate", produces = "application/json")
    public ResponseEntity<?> batchCreate(@RequestBody List<PurchaseOrderDto> newPurchaseOrders) {
        return ResponseEntity.ok(purchaseOrderModuleService.batchCreate(newPurchaseOrders));
    } 

    

}
