package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    
    @Id
    @JsonProperty(value="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @JsonProperty(value = "description")
    @Column(
        length = 30,
        nullable = false
    )
    private String description;


    @JsonProperty(value = "family")
    @Column(
        length = 30,
        nullable = false
    )
    private String family;


    @JsonProperty(value = "stock")
    @Column(nullable = false)
	private int stock;


    @JsonProperty(value = "price")
    @Column(nullable = false)
	private long price;

}
