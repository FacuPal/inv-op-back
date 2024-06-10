package com.inv.op.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductStockNotEnough;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderSaveError;
import com.inv.op.backend.error.sale.NewSaleSaveError;
import com.inv.op.backend.error.sale.SaleNotFoundError;
import com.inv.op.backend.error.sale.SaleSaveError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.model.Sale;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.PurchaseOrderRepository;
import com.inv.op.backend.repository.SaleRepository;

@Service
public class SaleModuleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductFamilyRepository productFamilyRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Collection<SaleDto> getSaleList() {
        return saleRepository.findAll()
                .stream()
                .map(sale -> modelMapper.map(sale, SaleDto.class))
                .toList();
    }

    public SaleDto getSale(Long id) {

        Optional<Sale> sale = saleRepository.findById(id);

        if (!sale.isPresent()) {
            throw new SaleNotFoundError();
        }

        return modelMapper.map(sale.get(), SaleDto.class);
    }

    public SaleDto saveNewSale(SaleDto requestBody) {
        if (requestBody.getSaleId() != null) {
            throw new NewSaleSaveError();
        }

        Optional<Product> optProduct;

        try {
            optProduct = productRepository.findById(requestBody.getProductId());
        } catch (Exception e) {
            throw new ProductNotFoundError();
        }

        if (!optProduct.isPresent() || optProduct.get().getIsDeleted()) {
            throw new ProductNotFoundError();
        }

        Product product = optProduct.get();

        if (!product.existStock(requestBody.getQuantity())) {
            throw new ProductStockNotEnough();
        }

        // Se reduce stock
        product.reduceStock(requestBody.getQuantity());
        productRepository.save(product);

        Sale sale = modelMapper.map(requestBody, Sale.class);
        sale.setProduct(product);
        sale.setSaleDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

        try {
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (product.lessThanSafeStock()) {
            createNewPurchaseOrder(product);
        }

        return modelMapper.map(sale, SaleDto.class);
    }

    public SaleDto updateSale(Long id, SaleDto requestBody) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (!sale.isPresent()) {
            throw new SaleNotFoundError();
        }

        Sale saleToUpdate = sale.get().updateValues(requestBody);

        try {
            saleRepository.save(saleToUpdate);
        } catch (Exception e) {
            throw new SaleSaveError();
        }

        return modelMapper.map(saleToUpdate, SaleDto.class);
    }

    private void createNewPurchaseOrder(Product product) {
        
        Collection<PurchaseOrder> openPurchaseOrder = purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, product.getProductId());

        if (!openPurchaseOrder.isEmpty()){
            //Ya hay una orden abierta para el producto.
            return;
        }

        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setProduct(product);
        newPurchaseOrder.setSupplier(product.getProductFamily().getSupplier());
        newPurchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
        newPurchaseOrder.setPurchaseOrderDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

        try {
            purchaseOrderRepository.save(newPurchaseOrder);
        } catch (Exception e) {
            throw new PurchaseOrderSaveError();
        }
        

    }

}
