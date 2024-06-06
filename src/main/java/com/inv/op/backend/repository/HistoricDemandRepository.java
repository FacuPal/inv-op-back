package com.inv.op.backend.repository;

import com.inv.op.backend.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.model.HistoricDemand;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface HistoricDemandRepository extends ListCrudRepository<HistoricDemand, Long>{

    Collection<HistoricDemand> findByProductAndYearGreaterThanEqualAndYearLessThanEqual(Product product, Integer desde, Integer hasta, Sort sort);

    Optional<HistoricDemand> findByProductAndYearAndMonth(Product product, Integer year, Integer month);

    @Query("SELECT h.quantity " +
            "FROM HistoricDemand h " +
            "WHERE h.month = :month AND h.year = :year " +
            "AND (" +
            "   :family = false AND h.product.productId = :id " +
            "   OR " +
            "   :family = true AND h.product.productId IN (" +
            "       SELECT p.productId FROM Product p WHERE p.productFamily.productFamilyId = :id" +
            "   )" +
            ")")
    Integer getPeriod(Long id, Boolean family, Integer month, Integer year);
}
