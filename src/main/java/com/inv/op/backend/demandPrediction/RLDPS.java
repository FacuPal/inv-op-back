package com.inv.op.backend.demandPrediction;

import com.inv.op.backend.dto.DTODemandPredictionModel;
import com.inv.op.backend.dto.DTODemandPredictionPeriod;
import com.inv.op.backend.dto.DTODemandRealPeriod;
import com.inv.op.backend.dto.DTODemandResults;
import com.inv.op.backend.model.*;
import com.inv.op.backend.repository.DemandPredictionModelRepository;
import com.inv.op.backend.repository.DemandPredictionModelTypeRepository;
import org.antlr.v4.runtime.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class RLDPS implements DemandPredictionStrategy {

    @Autowired
    private DemandPredictionModelRepository demandPredictionModelRepository;

    @Autowired
    private DemandPredictionModelTypeRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "RL";
    }
    @Override
    public Long create(DTODemandPredictionModel dto, ProductFamily family, Product product) throws Exception {
        Optional<DemandPredictionModelType> optDPMT = demandPredictionModelTypeRepository.findByDemandPredictionModelTypeName(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        RLDemandPredicitionModel rlDemandPredicitionModel = new RLDemandPredicitionModel();
        rlDemandPredicitionModel.setIgnorePeriods(dto.getIgnorePeriods());
        rlDemandPredicitionModel.setDemandPredictionModelType(optDPMT.get());
        rlDemandPredicitionModel.setDemandPredictionModelColor(dto.getColor());
        rlDemandPredicitionModel.setIsDeleted(false);
        rlDemandPredicitionModel.setProductFamily(family);
        rlDemandPredicitionModel.setProduct(product);
        return demandPredictionModelRepository.save(rlDemandPredicitionModel).getDemandPredictionModelId();
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

        RLDemandPredicitionModel rlDemandPredicitionModel = (RLDemandPredicitionModel)optDPM.get();
        rlDemandPredicitionModel.setIgnorePeriods(dto.getIgnorePeriods());
        rlDemandPredicitionModel.setDemandPredictionModelType(optDPMT.get());
        rlDemandPredicitionModel.setDemandPredictionModelColor(dto.getColor());
        demandPredictionModelRepository.save(rlDemandPredicitionModel);
    }

    @Override
    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception {
        Collection<DTODemandPredictionPeriod> periods = new ArrayList<>();
        Integer ignore = model.getIgnorePeriods();
        Integer predict = 3;

        Integer sumX = 0;
        Integer sumY = 0;
        BigDecimal sumX2 = BigDecimal.valueOf(0);
        BigDecimal sumXY = BigDecimal.valueOf(0);

        Integer n = 0;

        TreeMap<Integer, Integer> l = new TreeMap<>();

        Integer minX = 120000;
        Integer maxX = 0;

        for (DTODemandRealPeriod period : result.getPeriods()) {
            if (period.getValue() != null) {
                Integer x = period.getMonth() - 1 + period.getYear() * 12;
                l.put(x, period.getValue());
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
            }
        }

        SortedMap<Integer, Integer> sl = l.tailMap(minX + ignore);

        for (Map.Entry<Integer, Integer> e : sl.entrySet()) {
            Integer x = e.getKey();
            Integer y = e.getValue();
            n += 1;
            sumX += x;
            sumY += y;
            sumX2 = sumX2.add(BigDecimal.valueOf(Math.pow((double) x, 2.0)));
            sumXY = sumXY.add(BigDecimal.valueOf((long) x * y));
        }

        Double promX = (double) sumX / n;
        Double promY = (double) sumY / n;

        Double b = (sumXY.subtract(BigDecimal.valueOf(n * promX * promY))).divide(sumX2.subtract(BigDecimal.valueOf(n * Math.pow(promX, 2))), 12, RoundingMode.HALF_UP).doubleValue();
        Double a = promY - b * promX;

        for (Integer i = 1; i <= predict; i++) {
            sl.put(maxX + i, null);
        }

        for (Map.Entry<Integer, Integer> e : sl.entrySet()) {
            Integer month = (e.getKey() % 12 + 1);
            Integer year = (e.getKey() / 12);
            Integer x = e.getKey();
            Integer y = e.getValue();
            Double value = a + x * b;

            periods.add(DTODemandPredictionPeriod.builder()
                    .month(month)
                    .year(year)
                    .prediction(value)
                    .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), y, value))
                    .build());
        }


        return periods;
    }
}
