package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends ListCrudRepository<PurchaseOrder,Long> {
    
}
