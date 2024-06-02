package com.inv.op.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.inv.op.backend.service.DemandModuleService;

@RestController
@RequestMapping("/demandModule")
public class DemandModuleController {

    @Autowired
    DemandModuleService demandModuleService;

    @GetMapping(path = "/uploadHistoricDemand")
    public String getHistoricDemand(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @PostMapping(path = "/uploadHistoricDemand")
    public String uploadHistoricDemand(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @GetMapping(path = "/generalParameters")
    public String getGeneralParameters(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @PostMapping(path = "/generalParameters")
    public String saveGeneralParameters(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @GetMapping(path = "/demandPrediction")
    public String getDemandPrediction(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }

    @PostMapping(path = "/predictiNextPeriodDemand")
    public String predictNextPeriodDemand(@RequestBody String requestBody){
        throw new HttpServerErrorException(HttpStatusCode.valueOf(500), "Not implemented");
    }
    
}
