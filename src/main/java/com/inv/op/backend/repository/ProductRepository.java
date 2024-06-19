package com.inv.op.backend.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.inv.op.backend.dto.DTOProductoLista;
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

    Collection<Product> findByProductNameContainingIgnoreCase(String productName);
    List<Product> findAll();
    Optional<DTOProductoLista> findDTOProductoListaByProductId(Long id);
}