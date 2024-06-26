package com.inv.op.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @JsonProperty(value = "productId")
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @JsonProperty(value = "productName")
    @Column(name = "product_name", length = 30, nullable = false)
    private String productName;

    @JsonProperty(value = "productDescription")
    @Column(name = "product_description", length = 30, nullable = true)
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "product_family_id", nullable = false, referencedColumnName = "product_family_id", foreignKey = @ForeignKey(name = "FK_product_product_family"))
    @JsonProperty(value = "productFamily")
    private ProductFamily productFamily;

    // @JsonProperty(value = "optimalBatch")
    // @Column(name = "optimal_batch", nullable = false, columnDefinition = "int default 100")
    // private Integer optimalBatch;

    // @JsonProperty(value = "orderLimit")
    // @Column(name = "order_limit", nullable = false, columnDefinition = "int default 100")
    // private Integer orderLimit;

    @JsonProperty(value = "safeStock")
    @Column(name = "safe_stock", nullable = false, columnDefinition = "int default 0")
    private Integer safeStock;

    @JsonProperty(value = "stock")
    @Column(name = "stock", nullable = false, columnDefinition = "int default 0")
    private Integer stock;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean not null default false ")
    private Boolean isDeleted;

    // INVENTARIO

    @JsonProperty(value = "annualDemand")
    @Column(name = "annual_demand", nullable = false, columnDefinition = "int default 0")
    private Integer annualDemand;  // Tasa de demanda

    @JsonProperty(value = "leadTime")
    @Column(name = "lead_time", nullable = false, columnDefinition = "int default 0")
    private Integer leadTime;  // Tiempo de entrega

    @JsonProperty(value = "storageCost")
    @Column(name = "storage_cost", nullable = false, columnDefinition = "double default 0")
    private Double storageCost;

    @JsonProperty(value = "orderingCost")
    @Column(name = "ordering_cost", nullable = false, columnDefinition = "double default 0")
    private Double orderingCost;

    @JsonProperty(value = "unitCost")
    @Column(name = "unit_cost", nullable = false)
    private Double unitCost; // Costo de compra por unidad

    @JsonProperty(value = "cgi")
    @Column(name = "cgi", nullable = true)
    private Double cgi;

    public Product(Object o, String productName, String productDescription, ProductFamily productFamily, Integer optimalBatch, Integer orderLimit, Integer safeStock, Integer stock, boolean b) {
    }
  
    // DEVELOP

    @JsonProperty(value = "productDemand")
    @Column(name = "product_demand", nullable = false, columnDefinition = "int default 0")
    private Integer productDemand;

    @JsonProperty(value = "maxStock")
    @Column(name = "max_stock", nullable = false, columnDefinition = "int default 0")
    private Integer maxStock;

    @JsonProperty(value = "orderCost")
    @Column(name = "order_cost", nullable = false, columnDefinition = "int default 1")
    private Integer orderCost;

    @JsonProperty(value = "storageCost")
    @Column(name = "storage_cost", nullable = false, columnDefinition = "int default 1")
    private Integer storageCost;
    //HASTA ACÁ

    public Boolean existStock(Integer checkStock) { return stock >=  checkStock; }

    public void reduceStock(Integer reduceStock){stock -= reduceStock;}


    public Boolean lessThanOrderLimit() { return stock <= this.calculateOrderLimit();  }

    public String getInventoryModel() { return this.getProductFamily().getInventoryModel().getInventoryModelName();}

    public void addStock(Integer orderQuantity) { stock += orderQuantity; }

    public int calculateOptimalBatch() {
        int optimalBatch=0;

        switch (this.getInventoryModel().toLowerCase().trim()) {
            case "lote fijo":
                optimalBatch = (int)Math.ceil(Math.sqrt(2*this.orderCost*this.productDemand/this.storageCost));
                break;
            case "intervalo fijo": 
                optimalBatch = this.maxStock - this.stock;
                break;
        } 

        return optimalBatch > 0 ? optimalBatch : 0;
    }

    public Integer calculateOrderLimit() {
        return this.productDemand * this.productFamily.getSupplier().getSupplierDeliveryTime();
    }
}
