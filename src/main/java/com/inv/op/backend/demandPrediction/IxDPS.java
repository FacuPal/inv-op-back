package com.inv.op.backend.demandPrediction;

import com.inv.op.backend.dto.DTODemandPredictionModel;
import com.inv.op.backend.dto.DTODemandPredictionPeriod;
import com.inv.op.backend.dto.DTODemandResults;
import com.inv.op.backend.model.*;
import com.inv.op.backend.repository.DemandPredictionModelRepository;
import com.inv.op.backend.repository.DemandPredictionModelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class IxDPS implements DemandPredictionStrategy {

    @Autowired
    private DemandPredictionModelRepository demandPredictionModelRepository;

    @Autowired
    private DemandPredictionModelTypeRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "Ix";
    }

    @Override
    public Long create(DTODemandPredictionModel dto, ProductFamily family, Product product) throws Exception {
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        IxDemandPredictionModel ixDemandPredictionModel = new IxDemandPredictionModel();
        ixDemandPredictionModel.setLength(dto.getLength());
        ixDemandPredictionModel.setCount(dto.getCount());
        ixDemandPredictionModel.setExpectedDemand(dto.getExpectedDemand());
        ixDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        ixDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        ixDemandPredictionModel.setIsDeleted(false);
        ixDemandPredictionModel.setProductFamily(family);
        ixDemandPredictionModel.setProduct(product);
        return demandPredictionModelRepository.save(ixDemandPredictionModel).getDemandPredictionModelId();
    }

    @Override
    public void update(DTODemandPredictionModel dto) throws Exception {
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        Optional<DemandPredictionModel> optDPM = demandPredictionModelRepository.findById(dto.getId());
        if(optDPM.isEmpty()) {
            throw new Exception("No se encontró el modelo");
        }

        IxDemandPredictionModel ixDemandPredictionModel = (IxDemandPredictionModel)optDPM.get();
        ixDemandPredictionModel.setLength(dto.getLength());
        ixDemandPredictionModel.setCount(dto.getCount());
        ixDemandPredictionModel.setExpectedDemand(dto.getExpectedDemand());
        ixDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        ixDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        demandPredictionModelRepository.save(ixDemandPredictionModel);


    }

    @Override
    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception {
        return null;
    }
}
