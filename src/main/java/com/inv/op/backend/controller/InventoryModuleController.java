package com.inv.op.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inv.op.backend.service.InventoryModuleService;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/inventoryModule")
public class InventoryModuleController {
    
    @Autowired
    InventoryModuleService inventoryModuleService;

    @GetMapping(path = "/restockProduct")
    public String restockProduct(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @GetMapping(path = "/missingProduct")
    public String getProducts(){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }
}
