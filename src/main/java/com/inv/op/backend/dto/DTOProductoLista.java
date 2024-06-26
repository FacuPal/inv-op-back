package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.InventoryModel;
import com.inv.op.backend.model.Product;

import com.inv.op.backend.model.ProductFamily;
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
        this.safeStock= product.calculateSafetyStock();
        this.optimalBatch=product.calculateOptimalBatch();
        // this.orderLimit=product.getOrderLimit();
        this.isDeleted=product.getIsDeleted();
        this.productFamilyId=product.getProductFamily().getProductFamilyId();
    }

    private Long productId;
    private String productName;
    private String productDescription;
    private String productFamilyName;
    private String inventoryModelName;
    private Integer stock;
    private Double safeStock;
    private Integer orderLimit;
    private Integer optimalBatch;
    private Boolean isDeleted;
    private Long productFamilyId;
}
