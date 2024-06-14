package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTOGeneralDemandParameters {
    Integer periodosAPredecir;
    String metodoCalculoError;
    Double errorAceptable;
}
