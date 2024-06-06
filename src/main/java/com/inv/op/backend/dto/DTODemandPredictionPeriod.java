package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTODemandPredictionPeriod {
    Integer year;
    Integer month;
    Double prediction;
    Double error;
}
