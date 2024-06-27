package com.inv.op.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.InventoryModel;

@Repository
public interface InventoryModelRepository extends ListCrudRepository<InventoryModel, Long>{
    
    // InventoryModel findByInventoryModelNameIgnoreCase(String inventoryModelName);

}
