package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name="product_family")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductFamily {
    
    @Id
    @JsonProperty(value="productFamilyId")
    @Column(name = "product_family_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long productId;


    @JsonProperty(value = "productFamilyName")
    @Column(
        name = "product_family_name",
        length = 30,
        nullable = false,
        unique = true
    )
    private String productFamilyName;


    @JsonProperty(value = "supplier")
    @ManyToOne
    private Supplier supplier;


    @JsonProperty(value = "inventoryModel")
    @ManyToOne
    private InventoryModel inventoryModel;

    @OneToMany(mappedBy = "productFamily")
    private List<Product> products;


    @JsonProperty(value = "isDeleted")
    @Column(
        name = "is_deleted", 
        nullable = false
    )
	private Boolean isDeleted;

}
