package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name="inventory_model")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryModel {
    
    @Id
    @JsonProperty(value="inventoryModelId")
    @Column(name = "inventory_model_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long inventoryModelId;


    @JsonProperty(value = "inventoryModelName")
    @Column(
        name = "inventory_model_name",
        length = 30,
        nullable = false,
        unique = true
    )
    private String inventoryModelName;


    @OneToMany(mappedBy = "inventoryModel")
    // @ManyToMany(mappedBy = "inventoryModel"
    // @JoinTable(inverseJoinColumns=@JoinColumn(name="product_family_id"))
    @JsonProperty(value = "productFamilies")
    private List<ProductFamily> productFamilies;


    @JsonProperty(value = "isDeleted")
    @Column(
        name = "is_deleted", 
        nullable = false
    )
	private Boolean isDeleted;

}
