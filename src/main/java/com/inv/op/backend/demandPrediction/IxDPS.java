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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
        ixDemandPredictionModel.setExpectedDemand(dto.getExpectedDemand());
        ixDemandPredictionModel.setDemandPredictionModelType(optDPMT.get());
        ixDemandPredictionModel.setDemandPredictionModelColor(dto.getColor());
        demandPredictionModelRepository.save(ixDemandPredictionModel);


    }

    @Override
    public Collection<DTODemandPredictionPeriod> predict(DTODemandResults result, DTODemandPredictionModel model) throws Exception {
        Collection<DTODemandPredictionPeriod> periods = new ArrayList<>();
        Integer length = model.getLength();
        Integer expectedDemand = model.getExpectedDemand();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);

        TreeMap<Integer, Double> ix = new TreeMap<>();

        Integer i = 0;
        AtomicInteger atomicCantPeriodos = new AtomicInteger(0);

        AtomicInteger atomicOffsetPredecirMesActual = new AtomicInteger(0);

        result.getPeriods().forEach (p -> {
            if(p.getYear().equals(year) && p.getMonth().equals(month) && p.getValue() != null) atomicOffsetPredecirMesActual.set(1);
            if((p.getYear() < year || p.getYear().equals(year) && p.getMonth() < month)
                    || (p.getYear().equals(year) && p.getMonth().equals(month) && p.getValue() != null)) {
                atomicCantPeriodos.set(atomicCantPeriodos.get() + 1);
            }
        });

        Integer cantPeriodos = atomicCantPeriodos.get();
        Integer cantCiclos = cantPeriodos / length;
        Integer skip = cantPeriodos % length;
        Integer skipCopy = skip;

        if(cantCiclos == 0) throw new Exception("No hay suficientes datos para armar al menos un ciclo");

        Integer offsetPredecirMesActual = atomicOffsetPredecirMesActual.get();

        Integer sumaTotal = 0;

        for (DTODemandRealPeriod period : result.getPeriods()) {
            if (skipCopy > 0) {
                skipCopy--;
                continue;
            }
            Double value = 0.0;

            if (period.getValue() != null) {
                value = period.getValue().doubleValue();
            }
            Double prev = ix.getOrDefault(i, 0.0);

            ix.put(i, prev + value);
            sumaTotal += value.intValue();

            i = (i + 1) % length;
        }

        for (Map.Entry<Integer, Double> mes : ix.entrySet()) {
            Double prev = ix.get(mes.getKey());

            Double prom = prev / cantCiclos;
            ix.put(mes.getKey(), prom);
        }

        Double promedioTotal = sumaTotal.doubleValue() / cantCiclos.doubleValue();

        for (Map.Entry<Integer, Double> mes : ix.entrySet()) {
            Double prev = ix.get(mes.getKey());
            ix.put(mes.getKey(), prev/promedioTotal);
        }


        calendar.add(Calendar.MONTH, length + offsetPredecirMesActual);
        Date limite = calendar.getTime() ;
        calendar.add(Calendar.MONTH, -length);

        i = 0;

        Integer lastValue = 0;
        Integer lastPeriod = 0;
        for (DTODemandRealPeriod period : result.getPeriods()) {
            Integer aux = period.getMonth() - 1 + period.getYear() * 12;
            if (aux > lastPeriod && period.getValue() != null) {
                lastPeriod = aux;
                lastValue = period.getValue();
            }
        }

        periods.add(DTODemandPredictionPeriod.builder()
                //.month((month - 1 + offsetPredecirMesActual) % 12 + 1)
                //.year((month - 1 + offsetPredecirMesActual) % 12 == 0 ? year + 1 : year)
                .month(month - 1 + offsetPredecirMesActual)
                .year(year)
                .prediction(lastValue.doubleValue())
                .error(null)
                .build());

        while (calendar.getTime().before(limite)) {
            Double prediction = ix.get(i) * expectedDemand;
            Integer imonth = calendar.get(Calendar.MONTH) + 1;
            Integer iyear = calendar.get(Calendar.YEAR);

            periods.add(DTODemandPredictionPeriod.builder()
                    .month(imonth)
                    .year(iyear)
                    .prediction(prediction)
                    .error(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), null, prediction))
                    .build());
            calendar.add(Calendar.MONTH, 1);
            i = (i + 1) % length;
        }



        return periods;
    }
}
