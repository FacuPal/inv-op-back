package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto implements Serializable {

    public ProductResponseDto(Product product){
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        // this.optimalBatch = product.getOptimalBatch();
        // this.orderLimit = product.getOrderLimit();  
        this.safeStock = product.calculateSafetyStock();
        this.stock = product.getStock();
        // this.price = product.getPrice();
        this.productFamily = new ProductFamilyResponseDto(product.getProductFamily());
        this.isDeleted = product.getIsDeleted();
    }

    private String productName;
    private String productDescription;
    private Integer optimalBatch;
    private Integer orderLimit;
    private Double safeStock;
    private Integer stock;
    private Boolean isDeleted;
    private ProductFamilyResponseDto productFamily;

}
