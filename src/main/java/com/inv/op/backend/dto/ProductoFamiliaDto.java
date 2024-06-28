package com.inv.op.backend.dto;

import com.inv.op.backend.model.ProductFamily;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class ProductoFamiliaDto {
    public ProductoFamiliaDto(ProductFamily productFamily){
        this.productFamilyId=productFamily.getProductFamilyId();
        this.inventoryModelName=productFamily.getInventoryModel().getInventoryModelName();
        this.inventoryModelId= productFamily.getInventoryModel().getInventoryModelId();
        this.supplierName=productFamily.getSupplier().getSupplierName();
        this.supplierId=productFamily.getSupplier().getSupplierId();
    }
    private Long productFamilyId;
    private String productFamilyName;
    private String inventoryModelName;
    private Long inventoryModelId;
    private String supplierName;
    private Long supplierId;
}
