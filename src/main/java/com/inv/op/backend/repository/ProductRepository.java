package com.inv.op.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.model.Product;

@Repository
/**
 * ProductRepository
 */
public interface ProductRepository extends ListCrudRepository<Product, Long> {

    Optional<ProductDto> findProductByProductId(Long id);
}