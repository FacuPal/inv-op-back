package com.inv.op.backend.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseOrderDto {
    
    private Long purchaseOrderId;
    private Date purchaseOrderDate;
    private Long productId;
    private String productName;
    private String purchaseOrderStatus;
    private Long supplierId;
    private String supplierName;
    private Integer orderQuantity;
}
