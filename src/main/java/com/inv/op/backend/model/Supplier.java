package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name="supplier")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Supplier {
    
    @Id
    @JsonProperty(value="supplierId")
    @Column(name = "supplier_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long supplierId;


    @JsonProperty(value = "supplierName")
    @Column(
        name = "supplier_name",
        length = 30,
        nullable = false,
        unique = true
    )
    private String supplierName;


    @OneToMany(mappedBy = "supplier")
    @JsonProperty(value = "productFamilies")
    private List<ProductFamily> productFamilies;


    @JsonProperty(value = "supplierDeliveryTime")
    @Column(
        name = "supplier_delivery_time",
        nullable = false
    )
    private Integer supplierDeliveryTime;


    @JsonProperty(value = "isDeleted")
    @Column(
        name = "is_deleted", 
        nullable = false
    )
	private Boolean isDeleted;

}
