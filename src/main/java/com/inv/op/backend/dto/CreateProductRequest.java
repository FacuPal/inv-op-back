package com.inv.op.backend.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest implements Serializable {

    public CreateProductRequest(String productName, String productDescription, Long productFamilyId, Double storageCost, Double orderCost, Double unitCost,Integer stock, Integer productDemand, Integer maxStock ) {
        this.productName = productName;
        this.productDescription = productDescription;   
        this.productFamilyId = productFamilyId;
        this.orderLimit = 0;
        this.safeStock = 0; 
        this.stock = stock;
        this.maxStock = maxStock;
        this.orderCost = orderCost;
        this.storageCost = storageCost;
        this.productDemand = productDemand;
        this.unitCost = unitCost;
    }
    @JsonProperty(value = "productName")
    // @NotEmpty
    private String productName;

    @JsonProperty(value = "productDescription")
    private String productDescription;

    @JsonProperty(value = "productFamilyId")
    // @NotNull
    private Long productFamilyId;

    // @JsonProperty(value = "optimalBatch")
    // private Integer optimalBatch;

    @JsonProperty(value = "orderLimit")
    private Integer orderLimit;

    @JsonProperty(value = "safeStock")  
    private Integer safeStock;

    @JsonProperty(value = "stock")
    private Integer stock;    

    @JsonProperty(value = "productDemand")
    private Integer productDemand;

    @JsonProperty(value = "maxStock")
    private Integer maxStock;
    @JsonProperty(value = "orderCost")
    private Double orderCost;
    @JsonProperty(value = "storageCost")
    private Double storageCost;
    @JsonProperty(value = "unitCost")
    private Double unitCost;

}
