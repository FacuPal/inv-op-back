package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTODemandRealPeriod {
    Integer year;
    Integer month;
    Integer value;
}
