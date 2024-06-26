package com.inv.op.backend.repository;

import com.inv.op.backend.model.Parameter;
import com.inv.op.backend.model.ProductFamily;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ParameterRepository extends ListCrudRepository<Parameter, Long>{

    Parameter findByParameterNameIgnoreCase(String productFamilyName);
}
