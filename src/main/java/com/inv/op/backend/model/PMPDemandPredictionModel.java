package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

@Entity
@RequiredArgsConstructor
@DiscriminatorValue("PMP")
public class PMPDemandPredictionModel extends DemandPredictionModel{

    @JsonProperty(value = "pmpDemandPredictionModelPonderations")
    @Column(name = "pmp_demand_prediction_model_ponderations", length = 250, nullable = true)
    private String ponderations = "1";


    public String getRawPonderations() {
        return ponderations;
    }
    public Collection<String> getPonderations() {
        return Arrays.asList(ponderations.split(";"));
    }

    public void setPonderations(String ponderations) throws Exception {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(ponderations.split(";")));
        Pattern pattern = Pattern.compile("\\d+(,\\d+)?");
        for (String s : arr) {
            if(!pattern.matcher(s).matches()) {
                throw new Exception("El valor \"" + s + "\" no es una ponderación válida");
            }
        }
        this.ponderations = ponderations;
    }
}
