package com.inv.op.backend.dto;

import java.io.Serializable;

import com.inv.op.backend.model.InventoryModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryModelResponseDto implements Serializable{
    public InventoryModelResponseDto(InventoryModel inventoryModel){
        this.inventoryModelName = inventoryModel.getInventoryModelName();
    }

    private String inventoryModelName;
}
