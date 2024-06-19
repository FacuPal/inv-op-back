package com.inv.op.backend.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.inv.op.backend.dto.*;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.inv.op.backend.error.product.ProductFamilyNotFound;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductSaveError;
import com.inv.op.backend.error.supplier.SupplierNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;

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

        // if (!productFamily.isPresent()) {
        // throw new ProductFamilyNotFound();
        // }

        // Product(Long id, String name, String description, ProductFamily
        // productFamily, Integer optimalBatch, Integer orderLimit, Integer safeStock,
        // Integer stock, Boolean isDeleted

        Product product = new Product(null,
                newProduct.getProductName(),
                newProduct.getProductDescription(),
                productFamily,
                newProduct.getOptimalBatch(),
                newProduct.getOrderLimit(),
                newProduct.getSafeStock(),
                newProduct.getStock(),
                false);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductSaveError();
        }

        // return new DefaultResponseDto(HttpStatus.CREATED, "Product Created");
        return newProduct;

    }
    public Collection<ProductDto> getProductList() throws ProductNotFoundError {

        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
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
        product.setOptimalBatch(updatedProduct.getOptimalBatch());
        product.setStock(updatedProduct.getStock());
        product.setOrderLimit(updatedProduct.getOrderLimit());
        product.setSafeStock(updatedProduct.getSafeStock());

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

    public List<ProductoFamiliaDto> getAllProductFamilies() {
        return productFamilyRepository.findAll().stream()
                .map(family -> new ProductoFamiliaDto(family.getProductFamilyId(), family.getProductFamilyName()))
                .collect(Collectors.toList());
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


}