package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTOProductoLista implements Serializable {
    public DTOProductoLista(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productFamilyName = product.getProductFamily().getProductFamilyName();
        this.inventoryModelName = product.getProductFamily().getInventoryModel().getInventoryModelName();
        this.stock = product.getStock();
        this.safeStock= product.getSafeStock();
        this.optimalBatch=product.getOptimalBatch();
        this.orderLimit=product.getOrderLimit();

    }

    private Long productId;
    private String productName;
    private String productDescription;
    private String productFamilyName;
    private String inventoryModelName;
    private Integer stock;
    private Integer safeStock;
    private Integer orderLimit;
    private Integer optimalBatch;

}
