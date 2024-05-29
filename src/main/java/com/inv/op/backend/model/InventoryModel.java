package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Entity
@Table(name = "inventory_model")
@Data
@RequiredArgsConstructor
public class InventoryModel {

    @Id
    @JsonProperty(value = "inventoryModelId")
    @Column(name = "inventory_model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryModelId;

    @JsonProperty(value = "inventoryModelName")
    @Column(name = "inventory_model_name", length = 30, nullable = false, unique = true)
    private String inventoryModelName;

    @OneToMany(mappedBy = "inventoryModel")
    @JsonProperty(value = "productFamilies")
    private List<ProductFamily> productFamilies;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}
