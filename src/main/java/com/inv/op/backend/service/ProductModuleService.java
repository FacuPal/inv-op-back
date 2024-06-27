package com.inv.op.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import com.inv.op.backend.dto.*;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.model.*;
import com.inv.op.backend.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.inv.op.backend.error.product.ProductFamilyNotFound;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductSaveError;
import com.inv.op.backend.error.supplier.SupplierNotFoundError;
import com.inv.op.backend.dto.CreateProductRequest;
import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.dto.SupplierDto;
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
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;


    public CreateProductRequest saveProduct(CreateProductRequest newProduct) {

        ProductFamily productFamily = productFamilyRepository.findById(newProduct.getProductFamilyId())
                .orElseThrow(() -> new ProductFamilyNotFound());

        Product product = new Product();
        product.setProductName(newProduct.getProductName());
        product.setProductDescription(newProduct.getProductDescription());
        product.setProductFamily(productFamily);
        product.setStock(newProduct.getStock());
        product.setMaxStock(newProduct.getMaxStock());
        product.setOrderCost(newProduct.getOrderCost());
        product.setStorageCost(newProduct.getStorageCost());
        product.setProductDemand(newProduct.getProductDemand());
        product.setIsDeleted(false);
        product.setUnitCost(newProduct.getUnitCost());

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductSaveError();
        }
        return newProduct;

    }
    public Collection<ProductDto> getProductList() throws ProductNotFoundError {

        // return productRepository.findAll()
        //         .stream()
        //         .map(product -> modelMapper.map(product, ProductDto.class))
        //         .toList();

        Collection<ProductDto> productDtoList = new ArrayList<ProductDto>();

        Collection<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDto.setOptimalBatch(product.calculateOptimalBatch());
            productDtoList.add(productDto);
        }
        //         .stream()
        //         .map(product -> modelMapper.map(product, ProductDto.class))
        //         .toList();

        return productDtoList;
    }

    public Optional<ProductDto> getProduct(Long id) {

        Optional<ProductDto> product = productRepository.findProductByProductId(id);

        if (!product.isPresent()) {
            throw new ProductNotFoundError();
        }

        return product;
    }
    public List<DTOProductoLista> getAllProducts() {
        return productRepository.findAll().stream()
                .map(DTOProductoLista::new)
                .collect(Collectors.toList());
    }
    // Suppliers Services
    public SupplierDto getSupplier(Long id) {

        Optional<SupplierDto> supplier = supplierRepository.getSupplierDtoBySupplierId(id);

        if (!supplier.isPresent()) {
            throw new SupplierNotFoundError();
        }

        return supplier.get();
    }
    public DTOProductoLista getDTOProductoLista(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundError());

        return new DTOProductoLista(product);
    }
    public Product updateProduct(Long id, CreateProductRequest updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundError());

        product.setProductName(updatedProduct.getProductName());
        product.setProductDescription(updatedProduct.getProductDescription());
        product.setStock(updatedProduct.getStock());
        product.setProductDemand(updatedProduct.getProductDemand());
        product.setMaxStock(updatedProduct.getMaxStock());
        product.setStorageCost(updatedProduct.getStorageCost());
        product.setOrderCost(updatedProduct.getOrderCost());
        product.setUnitCost(updatedProduct.getUnitCost()); // Make sure unitCost is being set

        if (!product.getProductFamily().getProductFamilyId().equals(updatedProduct.getProductFamilyId())) {
            ProductFamily productFamily = productFamilyRepository.findById(updatedProduct.getProductFamilyId())
                    .orElseThrow(() -> new ProductFamilyNotFound());
            product.setProductFamily(productFamily);
        }

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductSaveError();
        }
        return product;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundError());

        // Verificar el estado de las órdenes de compra
        Collection<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, id);
        if (!purchaseOrders.isEmpty()) {
            throw new RuntimeException("No se puede eliminar el producto porque tiene orden/s de compra abierta/s.");
        }

        product.setIsDeleted(true);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductSaveError();
        }
    }
    public void restoreProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundError());

        // Solo restaurar si el producto está marcado como eliminado
        if (product.getIsDeleted()) {
            product.setIsDeleted(false);

            try {
                productRepository.save(product);
            } catch (Exception e) {
                throw new ProductSaveError();
            }
        } else {
            throw new RuntimeException("El producto no está marcado como eliminado.");
        }
    }
    public Collection<SupplierDto> getSupplierList() {
        return supplierRepository.findAll()
            .stream()
            .map(supplier -> modelMapper.map(supplier, SupplierDto.class))
            .toList();
    }
    public SupplierDto getDefaultSupplier(Long id) {
        Optional<Product> optProduct = productRepository.findById(id);

        if(!optProduct.isPresent()){
            throw new ProductNotFoundError();
        }

        return modelMapper.map(optProduct.get().getProductFamily().getSupplier(), SupplierDto.class);
    }
    public List<ProductoFamiliaDto> getAllProductFamilies() {
        return productFamilyRepository.findAll().stream()
                .map(family -> modelMapper.map(family, ProductoFamiliaDto.class))
                .collect(Collectors.toList());
    }

    public ProductoFamiliaDto saveProductFamily(ProductoFamiliaDto productFamilyDto) {
        ProductFamily productFamily = new ProductFamily();
        productFamily.setProductFamilyName(productFamilyDto.getProductFamilyName());

        try {
            Supplier supplier = supplierRepository.findById(productFamilyDto.getSupplierId())
                    .orElseThrow(() -> new SupplierNotFoundError());
            productFamily.setSupplier(supplier);
        } catch (SupplierNotFoundError e) {
            throw new RuntimeException("Supplier not found", e);
        }

        try {
            InventoryModel inventoryModel = inventoryModelRepository.findById(productFamilyDto.getInventoryModelId())
                    .orElseThrow(() -> new RuntimeException("Inventory Model not found"));
            productFamily.setInventoryModel(inventoryModel);
        } catch (RuntimeException e) {
            throw new RuntimeException("Inventory Model not found", e);
        }

        productFamily.setIsDeleted(false);
        productFamilyRepository.save(productFamily);
        return modelMapper.map(productFamily, ProductoFamiliaDto.class);
    }

    public void updateProductFamily(Long id, ProductoFamiliaDto updatedProductFamilyDto) {
        ProductFamily productFamily = productFamilyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Family not found"));

        productFamily.setProductFamilyName(updatedProductFamilyDto.getProductFamilyName());


        try {
            Supplier supplier = supplierRepository.findById(updatedProductFamilyDto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found"));
            productFamily.setSupplier(supplier);
        } catch (RuntimeException e) {
            throw new RuntimeException("Supplier not found", e);
        }

        try {
            InventoryModel inventoryModel = inventoryModelRepository.findById(updatedProductFamilyDto.getInventoryModelId())
                    .orElseThrow(() -> new RuntimeException("Inventory Model not found"));
            productFamily.setInventoryModel(inventoryModel);
        } catch (RuntimeException e) {
            throw new RuntimeException("Inventory Model not found", e);
        }

        productFamilyRepository.save(productFamily);
    }

    public ProductoFamiliaDto getProductFamily(Long id) {
        ProductFamily productFamily = productFamilyRepository.findById(id)
                .orElseThrow(() -> new ProductFamilyNotFound());

        return modelMapper.map(productFamily, ProductoFamiliaDto.class);
    }
    public List<DTOInventoryModel> getAllInventoryModels() {
        return inventoryModelRepository.findAll().stream()
                .map(inventory -> modelMapper.map(inventory,DTOInventoryModel.class))
                .collect(Collectors.toList());
    }
    public List<DTOSupplier> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplier -> modelMapper.map(supplier,DTOSupplier.class))
                        .collect(Collectors.toList());
    }
}