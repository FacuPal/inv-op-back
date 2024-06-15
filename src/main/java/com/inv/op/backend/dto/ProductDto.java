package com.inv.op.backend.dto;

import java.util.Date;

import com.inv.op.backend.model.ProductFamily;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private Long productFamilyId;
    private String productFamilyName;
    private Integer stock;
    private Integer safeStock;
    private Integer orderLimit;
    private Integer optimalBatch;
    private Boolean isDeleted;
}
