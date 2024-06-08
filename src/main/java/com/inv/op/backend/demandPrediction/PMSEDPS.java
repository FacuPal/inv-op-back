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
import java.util.Optional;

@Component
public class PMSEDPS implements DemandPredictionStrategy {

    @Autowired
    private DemandPredictionModelRepository demandPredictionModelRepository;

    @Autowired
    private DemandPredictionModelTypeRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "PMSE";
    }
    @Override
    public Long create(DTODemandPredictionModel dto, ProductFamily family, Product product) throws Exception {
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        PMSEDemandPredictionModel pmseDemandPredictionModel = new PMSEDemandPredictionModel();
        pmseDemandPredictionModel.setAlpha(dto.getAlpha());
        pmseDemandPredictionModel.setRoot(dto.getRoot());
        pmseDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        pmseDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        pmseDemandPredictionModel.setIsDeleted(false);
        pmseDemandPredictionModel.setProductFamily(family);
        pmseDemandPredictionModel.setProduct(product);
        return demandPredictionModelRepository.save(pmseDemandPredictionModel).getDemandPredictionModelId();
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

        PMSEDemandPredictionModel pmseDemandPredictionModel = (PMSEDemandPredictionModel)optDPM.get();
        pmseDemandPredictionModel.setAlpha(dto.getAlpha());
        pmseDemandPredictionModel.setRoot(dto.getRoot());
        pmseDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        pmseDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        demandPredictionModelRepository.save(pmseDemandPredictionModel);
    }

    @Override
    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception {
        Collection<DTODemandPredictionPeriod> periods = new ArrayList<>();
        Double a = model.getAlpha();
        Double prev = null;
        Integer trdPrev = null;

        Integer maxMonth = 0;
        Integer maxYear = 0;
        for (DTODemandRealPeriod period : result.getPeriods()) {
            if (prev == null) {
                prev = model.getRoot();
            } else {
                prev = prev + a * (trdPrev - prev);
            }

            periods.add(DTODemandPredictionPeriod.builder()
                    .month(period.getMonth())
                    .year(period.getYear())
                    .prediction(prev)
                    .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), period.getValue(), prev))
                    .build());


            if (period.getValue() == null) {
                trdPrev = prev.intValue();
            } else {
                trdPrev = period.getValue();
            }

            if(period.getYear() > maxYear || period.getYear().equals(maxYear) && period.getMonth() - 1  > maxMonth) {
                maxYear = period.getYear();
                maxMonth = period.getMonth() - 1;
            }
        }
        /*
        if (prev != null) {
            prev = prev + a * (trdPrev - prev);
            periods.add(DTODemandPredictionPeriod.builder()
                    .month((maxMonth + 1) % 12 + 1)
                    .year(maxMonth == 11 ? maxYear + 1 : maxYear)
                    .prediction(prev)
                    .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), null, prev))
                    .build());
        }*/
        return periods;
    }
}
