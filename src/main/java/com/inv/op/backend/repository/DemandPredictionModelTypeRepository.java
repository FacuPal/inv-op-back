package com.inv.op.backend.repository;

import com.inv.op.backend.model.DemandPredictionModelType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DemandPredictionModelTypeRepository extends ListCrudRepository<DemandPredictionModelType, Long>{

    public Optional<DemandPredictionModelType> findByDemandPredictionModelTypeName(String name);
}
