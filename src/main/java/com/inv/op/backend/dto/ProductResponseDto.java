package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto implements Serializable {

    public ProductResponseDto(Product product){
        this.family = product.getFamily();
        this.stock = product.getStock();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    private String description;
    private String family;
	private int stock;
    private long price;

}
