package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTOProductOrFamily {
    Long id;
    Boolean family;
    String name;
}
