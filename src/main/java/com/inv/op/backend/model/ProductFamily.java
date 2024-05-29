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
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "product_family")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductFamily {

    @Id
    @JsonProperty(value = "productFamilyId")
    @Column(name = "product_family_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productFamilyId;

    @JsonProperty(value = "productFamilyName")
    @Column(name = "product_family_name", length = 30, nullable = false, unique = true)
    private String productFamilyName;

    @JsonProperty(value = "supplier")
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id", foreignKey = @ForeignKey(name = "FK_product_family_supplier"))
    private Supplier supplier;

    @JsonProperty(value = "inventoryModel")
    @ManyToOne
    @JoinColumn(name = "inventory_model_id", referencedColumnName = "inventory_model_id", foreignKey = @ForeignKey(name = "FK_product_family_inventory_model"))
    private InventoryModel inventoryModel;

    // @OneToMany(mappedBy = "productFamily")
    // private List<Product> products;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}
