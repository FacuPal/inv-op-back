package com.inv.op.backend.dto;

import com.inv.op.backend.model.ProductFamily;

public interface ProductDto {
    String getProductName();

    String getProductDescription();

    ProductFamily getProductFamily();
}
