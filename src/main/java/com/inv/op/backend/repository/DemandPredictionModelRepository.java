package com.inv.op.backend.repository;

import com.inv.op.backend.model.DemandPredictionModel;
import com.inv.op.backend.model.InventoryModel;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DemandPredictionModelRepository extends ListCrudRepository<DemandPredictionModel, Long>{

    Collection<DemandPredictionModel> findByIsDeletedFalse();
}
