package com.inv.op.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ProductoFamiliaDto {
    private Long productFamilyId;
    private String productFamilyName;
}
