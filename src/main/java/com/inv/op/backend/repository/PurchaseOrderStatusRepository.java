package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.inv.op.backend.model.PurchaseOrderStatus;

public interface PurchaseOrderStatusRepository extends ListCrudRepository<PurchaseOrderStatus, Long> {
    
}
