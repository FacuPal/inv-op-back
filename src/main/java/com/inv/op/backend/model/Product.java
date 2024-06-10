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

    @JsonProperty(value = "optimalBatch")
    @Column(name = "optimal_batch", nullable = false, columnDefinition = "int default 100")
    private Integer optimalBatch;

    @JsonProperty(value = "orderLimit")
    @Column(name = "order_limit", nullable = false, columnDefinition = "int default 100")
    private Integer orderLimit;

    @JsonProperty(value = "safeStock")
    @Column(name = "safe_stock", nullable = false, columnDefinition = "int default 0")
    private Integer safeStock;

    @JsonProperty(value = "stock")
    @Column(name = "stock", nullable = false, columnDefinition = "int default 0")
    private Integer stock;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean not null default false ")
    private Boolean isDeleted;

    public Boolean existStock(Integer checkStock) { return stock >=  checkStock; }

    public void reduceStock(Integer reduceStock){stock -= reduceStock;}
}
