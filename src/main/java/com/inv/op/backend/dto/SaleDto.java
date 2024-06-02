package com.inv.op.backend.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SaleDto {
    
    private Long saleId;
    private String customerName;
    private Long productId;
    private Integer quantity;
    private Date saleDate;
    
    // public Sale toSale() {
    //     Sale sale = new Sale();
    //     sale.setSaleId(saleId);
    //     sale.setCustomerName(customerName);
    //     sale.setProduct(new Product(productId));
    //     sale.setQuantity(quantity);
    //     return sale;
    // }
}
