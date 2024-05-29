package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.inv.op.backend.model.PurchaseOrder;

public interface PurchaseOrderRepository extends ListCrudRepository<PurchaseOrder,Long> {
    
}
