package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.NextPeriodPrediction;

@Repository
public interface NextPeriodPredictionRepository extends ListCrudRepository<NextPeriodPrediction, Long>{
    
}
