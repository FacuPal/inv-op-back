package com.inv.op.backend.controller;

import com.inv.op.backend.dto.DTODemandaHistoricaAnual;
import com.inv.op.backend.dto.DTOError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import com.inv.op.backend.service.DemandModuleService;

import java.util.Collection;

@RestController
@RequestMapping("/demandModule")
public class DemandModuleController {

    @Autowired
    DemandModuleService demandModuleService;

    @GetMapping(path = "/products/{search}")
    public ResponseEntity<?> getProducts(@PathVariable String search){
        try {
            return ResponseEntity.ok(demandModuleService.getProducts(search));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }
    @GetMapping(path = "/historicDemand/{articulo}")
    public ResponseEntity<?> getHistoricDemand(@PathVariable Long articulo, @RequestParam("desde") Integer desde, @RequestParam("hasta") Integer hasta){
        try {
            return ResponseEntity.ok(demandModuleService.getHistoricDemand(articulo, desde, hasta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @PostMapping(path = "/historicDemand/{articulo}")
    public ResponseEntity<?> uploadHistoricDemand(@PathVariable Long articulo, @RequestBody Collection<DTODemandaHistoricaAnual> dtos){
        try {
            return ResponseEntity.ok(demandModuleService.postHistoricDemand(articulo, dtos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping(path = "/generalParameters")
    public ResponseEntity<?> getGeneralParameters(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @PostMapping(path = "/generalParameters")
    public ResponseEntity<?> saveGeneralParameters(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @GetMapping(path = "/demandPrediction")
    public ResponseEntity<?> getDemandPrediction(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @PostMapping(path = "/predictNextPeriodDemand")
    public ResponseEntity<?> predictNextPeriodDemand(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }
    
}
