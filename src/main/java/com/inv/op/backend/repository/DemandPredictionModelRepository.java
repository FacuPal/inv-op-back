package com.inv.op.backend.repository;

import com.inv.op.backend.model.DemandPredictionModel;
import com.inv.op.backend.model.InventoryModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DemandPredictionModelRepository extends ListCrudRepository<DemandPredictionModel, Long>{

    @Query("SELECT m " +
            "FROM DemandPredictionModel m " +
            "WHERE m.isDeleted = false " +
            "AND (:familia = false AND m.product.productId = :id OR :familia = true AND m.productFamily.productFamilyId = :id)")
    Collection<DemandPredictionModel> buscarPorProductoOFamilia(Long id, Boolean familia);

}
