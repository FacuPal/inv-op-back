package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class DTODemandPrediction {
    Long id;
    String type;
    String color;
    Integer num;
    @Builder.Default
    Collection<DTODemandPredictionPeriod> periods = new ArrayList<>();
}
