package com.inv.op.backend.controller;

import com.inv.op.backend.dto.DTODemandPredictionModel;
import com.inv.op.backend.dto.DTODemandaHistoricaAnual;
import com.inv.op.backend.dto.DTOError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import com.inv.op.backend.service.DemandModuleService;

import java.util.Collection;
import java.util.Date;

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


    @GetMapping(path = "/productsAndFamilies/{search}")
    public ResponseEntity<?> getProductsAndFamilies(@PathVariable String search){
        try {
            return ResponseEntity.ok(demandModuleService.getProductsAndFamilies(search));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }


    @GetMapping(path = "/model/{id}")
    public ResponseEntity<?> getModels(@PathVariable Long id, @RequestParam("family") Boolean family){
        try {
            return ResponseEntity.ok(demandModuleService.getModels(id, family));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @PutMapping(path = "/model/{id}")
    public ResponseEntity<?> putModel(@RequestBody DTODemandPredictionModel dto, @PathVariable Long id, @RequestParam("family") Boolean family){
        try {
            Long ret = demandModuleService.putModel(dto, id, family);
            return ResponseEntity.ok("{\"id\": " + ret + "}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @DeleteMapping(path = "/model/{id}")
    public ResponseEntity<?> deleteModel(@PathVariable Long id){
        try {
            demandModuleService.deleteModel(id);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    @GetMapping(path = "/demandPrediction/{id}")
    public ResponseEntity<?> getDemandPrediction(@PathVariable Long id, @RequestParam("family") Boolean family, @RequestParam("desde") Long desde, @RequestParam("predecirMesActual") Boolean predecirMesActual){
        try {
            return ResponseEntity.ok(demandModuleService.predict(id, family, new Date(desde), predecirMesActual));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new DTOError(e.getMessage()));
        }
    }

    
}
