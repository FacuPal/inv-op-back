package com.inv.op.backend.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// public interface SupplierDto {

//     Long getSupplierId();
    
//     String getSupplierName();

//     String getSupplierDeliveryTime();
// }
public class SupplierDto {

    private Long supplierId;
    private String supplierName;
    private Integer supplierDeliveryTime;
}
