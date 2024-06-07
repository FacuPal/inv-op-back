package com.inv.op.backend.demandPrediction;

import com.inv.op.backend.dto.DTODemandPredictionModel;
import com.inv.op.backend.dto.DTODemandPredictionPeriod;
import com.inv.op.backend.dto.DTODemandRealPeriod;
import com.inv.op.backend.dto.DTODemandResults;
import com.inv.op.backend.model.*;
import com.inv.op.backend.repository.DemandPredictionModelRepository;
import com.inv.op.backend.repository.DemandPredictionModelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Component
public class PMPDPS implements DemandPredictionStrategy{

    @Autowired
    private DemandPredictionModelRepository demandPredictionModelRepository;

    @Autowired
    private DemandPredictionModelTypeRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "PMP";
    }

    @Override
    public Long create(DTODemandPredictionModel dto, ProductFamily family, Product product) throws Exception {
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        PMPDemandPredictionModel pmpDemandPredictionModel = new PMPDemandPredictionModel();
        pmpDemandPredictionModel.setPonderations(dto.getPonderations());
        pmpDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        pmpDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        pmpDemandPredictionModel.setIsDeleted(false);
        pmpDemandPredictionModel.setProductFamily(family);
        pmpDemandPredictionModel.setProduct(product);
        return demandPredictionModelRepository.save(pmpDemandPredictionModel).getDemandPredictionModelId();
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

        PMPDemandPredictionModel pmpDemandPredictionModel = (PMPDemandPredictionModel)optDPM.get();
        pmpDemandPredictionModel.setPonderations(dto.getPonderations());
        pmpDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        pmpDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        demandPredictionModelRepository.save(pmpDemandPredictionModel);
    }

    @Override
    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception {
        Collection<DTODemandPredictionPeriod> periods = new ArrayList<>();
        ArrayList<Double> ponderaciones = new ArrayList<>(((PMPDemandPredictionModel) demandPredictionModelRepository.findById(model.getId()).get()).getPonderations());
        Double sumaPonderaciones = 0.0;
        for (Double ponderacion : ponderaciones) {
            sumaPonderaciones += ponderacion;
        }
        LinkedList<Integer> prev = new LinkedList<>();
        Integer maxMonth = 0;
        Integer maxYear = 0;
        for (DTODemandRealPeriod period : result.getPeriods()) {
            if (prev.size() < ponderaciones.size()) {
                prev.addLast(period.getValue());
            } else {
                Double value = 0.0;
                for(int i = 0; i < ponderaciones.size(); i++) {
                    value += ponderaciones.get(i) * prev.get(i) / sumaPonderaciones;
                }
                periods.add(DTODemandPredictionPeriod.builder()
                        .month(period.getMonth())
                        .year(period.getYear())
                        .prediction(value)
                        .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), period.getValue(), value))
                        .build());
                prev.removeFirst();
                prev.addLast(period.getValue());
                if(period.getYear() > maxYear || period.getYear().equals(maxYear) && period.getMonth() - 1  > maxMonth) {
                    maxYear = period.getYear();
                    maxMonth = period.getMonth() - 1;
                }
            }
        }
        if ( prev.size() == ponderaciones.size()) {
            Double value = 0.0;
            for(int i = 0; i < ponderaciones.size(); i++) {
                value += ponderaciones.get(i) * prev.get(i) / sumaPonderaciones;
            }
            periods.add(DTODemandPredictionPeriod.builder()
                    .month((maxMonth + 1) % 12 + 1)
                    .year(maxMonth == 11 ? maxYear + 1 : maxYear)
                    .prediction(value)
                    .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), null, value))
                    .build());
        }
        return periods;
    }
}
