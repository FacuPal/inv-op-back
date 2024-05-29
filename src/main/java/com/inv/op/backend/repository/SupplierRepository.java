package com.inv.op.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.dto.SupplierDto;
import com.inv.op.backend.model.Supplier;

@Repository
public interface SupplierRepository extends ListCrudRepository<Supplier, Long>{
    Optional<SupplierDto> getSupplierDtoBySupplierId(Long supplierId);
}
