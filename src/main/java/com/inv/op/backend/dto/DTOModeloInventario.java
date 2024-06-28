package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTOModeloInventario {
    Long id;
    String nombre;
    String familia;
    String modeloInventario;
    Integer optimalBatch;
    Integer orderLimit;
    Double safetyStock;
    Double cgi;
}
