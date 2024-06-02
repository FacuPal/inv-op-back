package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTODemandaHistoricaMensual {
    Integer mes;
    Integer cantidad;
}
