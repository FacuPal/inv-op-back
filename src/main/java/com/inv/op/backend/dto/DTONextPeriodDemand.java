package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class DTONextPeriodDemand {

    Integer month;
    Integer year;
    Integer quantity;
    Long model;
}
