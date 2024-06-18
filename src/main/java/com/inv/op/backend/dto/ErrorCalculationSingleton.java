package com.inv.op.backend.demandPrediction;

import com.inv.op.backend.repository.ParameterRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorCalculationSingleton {

    @Autowired
    private ParameterRepository parameterRepository;

    public String getMetodoErrorGlobal() {
        return parameterRepository.findByParameterNameIgnoreCase("METODO_CALCULO_ERROR").getParameterValue();
    }
    public Double getError(String metodo, Integer demandaReal, Double prediccion) throws Exception {
        if (demandaReal == null || prediccion == null) return null;
        switch (metodo) {
            case "MAD":
                return Math.abs(demandaReal - prediccion);
            case "MSE":
                return Math.pow(demandaReal - prediccion, 2);
            case "MAPE":
                return Math.abs(demandaReal - prediccion) / demandaReal;
            default:
                throw new Exception("Método de cálculo de error no soportado: \"" + metodo + "\"");
        }
    }
}
