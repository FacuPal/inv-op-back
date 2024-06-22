package com.inv.op.backend.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.model.Product;

@Repository
/**
 * ProductRepository
 */
public interface ProductRepository extends ListCrudRepository<Product, Long> {

    Optional<ProductDto> findProductByProductId(Long id);

    Collection<Product> findByProductNameContainingIgnoreCase(String productName);


    @Query("SELECT p FROM Product p WHERE p.stock <= p.orderLimit AND p.isDeleted = false AND p.ordenDeCompraPendiente = false")
    List<Product> findRestockProducts();

    @Query("SELECT p FROM Product p WHERE p.stock <= p.safeStock AND p.isDeleted = false")
    List<Product> findMissingProducts();


}