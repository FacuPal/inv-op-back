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

    public CreateProductRequest(String productName, String productDescription, Long productFamilyId) {
        this.productName = productName;
        this.productDescription = productDescription;   
        this.productFamilyId = productFamilyId;
        // this.optimalBatch = 0;
        this.orderLimit = 0;
        this.safeStock = 0; 
        this.stock = 0;
        this.maxStock = 0;
        this.orderCost = 0.0;
        this.storageCost = 0.0;
        this.productDemand = 0;
        this.unitCost = 0.0;

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
