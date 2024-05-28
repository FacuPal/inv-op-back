package com.inv.op.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.ProductResponseDto;
import com.inv.op.backend.dto.SupplierResponseDto;
import com.inv.op.backend.error.ProductNotFoundError;
import com.inv.op.backend.error.ProductSaveError;
import com.inv.op.backend.error.SupplierNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.Supplier;
import com.inv.op.backend.repository.InventoryModelRepository;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.SupplierRepository;

@Service
public class ProductModuleService {
    
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductFamilyRepository productFamilyRepository;
    @Autowired
    InventoryModelRepository inventoryModelRepository;
    @Autowired
    SupplierRepository supplierRepository;

    //Product services
    // public ProductResponseDto saveProduct(Product newProduct) {

    //     ProductResponseDto productResponseDto = new ProductResponseDto();

    //     try {
    //         productRepository.save(newProduct);
    //     } catch (Exception e) {
    //         throw new ProductSaveError();
    //     }

    //     return productResponseDto;

    // }

    public ProductResponseDto getProduct(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) {
            throw new ProductNotFoundError();            
        }

        return new ProductResponseDto(product.get());
    }

    //Suppliers Services
    public SupplierResponseDto getSuppliers(Long id) {

        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (!supplier.isPresent()) {
            throw new SupplierNotFoundError();
        }

        return new SupplierResponseDto(supplier.get());
    }
    
}
