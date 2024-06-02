package com.inv.op.backend.repository;

import com.inv.op.backend.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.HistoricDemand;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface HistoricDemandRepository extends ListCrudRepository<HistoricDemand, Long>{

    Collection<HistoricDemand> findByProductAndYearGreaterThanEqualAndYearLessThanEqual(Product product, Integer desde, Integer hasta, Sort sort);

    Optional<HistoricDemand> findByProductAndYearAndMonth(Product product, Integer year, Integer month);
}
