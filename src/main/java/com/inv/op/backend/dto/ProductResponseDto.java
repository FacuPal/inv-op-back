package com.inv.op.backend.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto implements Serializable {

    private String description;
    private String family;
	private int stock;
    private long price;

}
