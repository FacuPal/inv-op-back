package com.inv.op.backend.demandPrediction;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DPSFactory {

    @Autowired
    private List<DemandPredictionStrategy> strategyList;

    private static final Map<String, DemandPredictionStrategy> strategyMap = new HashMap<>();

    @PostConstruct
    public void initMyServiceCache() {
        for(DemandPredictionStrategy strategy : strategyList) {
            strategyMap.put(strategy.getType(), strategy);
        }
    }

    public DemandPredictionStrategy getStrategy(String type) throws Exception {
        DemandPredictionStrategy ret = strategyMap.get(type);
        if(ret == null) throw new Exception("Tipo de modelo \"" + type + "\" no soportado");
        return ret;
    }
}
