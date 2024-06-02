package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.HistoricDemand;

@Repository
public interface HistoricDemandRepository extends ListCrudRepository<HistoricDemand, Long>{

    
}
