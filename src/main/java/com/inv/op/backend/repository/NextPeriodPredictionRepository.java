package com.inv.op.backend.repository;

import com.inv.op.backend.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.NextPeriodPrediction;

import java.util.Optional;

@Repository
public interface NextPeriodPredictionRepository extends ListCrudRepository<NextPeriodPrediction, Long>{

    Optional<NextPeriodPrediction> findByProductAndMonthAndYear(Product product, Integer month, Integer year);
}
