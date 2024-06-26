package com.inv.op.backend.repository;

import com.inv.op.backend.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.dto.ProductFamilyDto;
import com.inv.op.backend.model.ProductFamily;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductFamilyRepository extends ListCrudRepository<ProductFamily, Long>{
    
    ProductFamilyDto getProductFamilyByProductFamilyId(Long Id);
    List<ProductFamily> findAll();

    Collection<ProductFamily> findByProductFamilyNameContainingIgnoreCase(String productFamilyName);
}
