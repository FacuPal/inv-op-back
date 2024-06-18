package com.inv.op.backend.demandPrediction;

import com.inv.op.backend.dto.DTODemandPredictionModel;
import com.inv.op.backend.dto.DTODemandPredictionPeriod;
import com.inv.op.backend.dto.DTODemandResults;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;

import java.util.Collection;

public interface DemandPredictionStrategy {

    public String getType();

    public Long create(DTODemandPredictionModel dto, ProductFamily family, Product product) throws Exception;

    public void update(DTODemandPredictionModel dto) throws Exception;

    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception;
}
