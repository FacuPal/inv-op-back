package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
public class DTODemandaHistoricaAnual {
    Integer ano;
    @Builder.Default
    Collection<DTODemandaHistoricaMensual> meses = new ArrayList<>();
}
