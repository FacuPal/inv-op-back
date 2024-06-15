package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class DTODemandResults {
    @Builder.Default
    Collection<DTODemandRealPeriod> periods = new ArrayList<>();

    @Builder.Default
    Collection<DTODemandPrediction> predictions = new ArrayList<>();

    DTONextPeriodDemand nextPeriodDemand;
}
