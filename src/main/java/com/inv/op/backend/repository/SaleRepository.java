package com.inv.op.backend.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.Sale;

@Repository
public interface SaleRepository extends ListCrudRepository<Sale, Long> {
    
}
