package com.inv.op.backend.dto;

import com.inv.op.backend.model.Supplier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTOSupplier {
    public DTOSupplier(Supplier supplier){
        this.supplierId=supplier.getSupplierId();
        this.supplierName=supplier.getSupplierName();
    }

    private String supplierName;
    private Long supplierId;

}
