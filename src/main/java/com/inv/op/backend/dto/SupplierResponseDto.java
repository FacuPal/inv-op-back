package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.Supplier;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupplierResponseDto implements Serializable{

    public SupplierResponseDto(Supplier supplier) {
        this.supplierName = supplier.getSupplierName();
        this.supplierDeliveryTime = supplier.getSupplierDeliveryTime();
    }

    private String supplierName;
    private Integer supplierDeliveryTime;
    
}
