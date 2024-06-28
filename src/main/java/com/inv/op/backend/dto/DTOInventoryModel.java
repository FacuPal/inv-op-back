package com.inv.op.backend.dto;

import com.inv.op.backend.model.InventoryModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTOInventoryModel {
    public DTOInventoryModel(InventoryModel inventoryModel){
        this.inventoryModelId=inventoryModel.getInventoryModelId();
        this.inventoryModelName=inventoryModel.getInventoryModelName();
    }
    private String inventoryModelName;
    private Long inventoryModelId;
}
