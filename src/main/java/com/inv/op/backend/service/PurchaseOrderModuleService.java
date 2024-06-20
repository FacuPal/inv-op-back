package com.inv.op.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderCreateError;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderNotFound;
import com.inv.op.backend.error.supplier.SupplierNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.model.Supplier;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.PurchaseOrderRepository;
import com.inv.op.backend.repository.SupplierRepository;

@Service
public class PurchaseOrderModuleService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierRepository supplierRepository;    

    @Autowired
    ModelMapper modelMapper;

    public Collection<PurchaseOrderDto> getPurchaseOrderList() {

        // Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, new Long(1));
        Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findByPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
        
        return purchaseOrderList.stream().map(purchase -> modelMapper.map(purchase, PurchaseOrderDto.class)).toList();
    }

    public PurchaseOrderDto getPurchaseOrder(Long purchaseOrderId) {
        
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);

        if (!purchaseOrder.isPresent()) {
            throw new PurchaseOrderNotFound();
        }

        return modelMapper.map(purchaseOrder.get(), PurchaseOrderDto.class);

    }

    public Object createPurchaseOrder(PurchaseOrderDto requestBody) {

        if (requestBody.getPurchaseOrderId() != null) {
            throw new PurchaseOrderCreateError();
        }
        //TODO: Agregar validación de si hay una orden de compra abierta para el producto.

        Optional<Product> optProduct = productRepository.findById(requestBody.getProductId());
        Optional<Supplier> optSupplier = supplierRepository.findById(requestBody.getSupplierId());
        if (!optProduct.isPresent()) {
            throw new ProductNotFoundError();
        }

        if (!optSupplier.isPresent()){
            throw new SupplierNotFoundError();
        }

        PurchaseOrder newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setProduct(optProduct.get());
        newPurchaseOrder.setSupplier(optSupplier.get());
        newPurchaseOrder.setOrderQuantity(requestBody.getOrderQuantity());
        newPurchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
        newPurchaseOrder.setPurchaseOrderDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

        return modelMapper.map(purchaseOrderRepository.save(newPurchaseOrder), PurchaseOrderDto.class);

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'createPurchaseOrder'");
    }

    //TODO: Agregar proceso para calcular ordenes de compra de intervalo fijo. 

    
    
}
