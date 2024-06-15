package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.ProductFamily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFamilyResponseDto implements Serializable {
    public ProductFamilyResponseDto(ProductFamily productFamily) {
        this.productFamilyName = productFamily.getProductFamilyName();
        this.isDeleted = productFamily.getIsDeleted();
        this.supplier = new SupplierResponseDto(productFamily.getSupplier());
    }

    private String productFamilyName;
    // private InventoryModel inventoryModel;
    private SupplierResponseDto supplier;
    private Boolean isDeleted;
    

}
