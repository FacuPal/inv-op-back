package com.inv.op.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.DefaultResponseDto;
import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.purchaseOrder.EmptyPurchaseOrderList;
import com.inv.op.backend.error.purchaseOrder.NoneNewPurchaseOrdersError;
import com.inv.op.backend.error.purchaseOrder.OpenPurchaseOrderExistsForProduct;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderCreateError;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderNotFound;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderNotOpen;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderSaveError;
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
        // Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findByPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
        Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findAll();
        
        return purchaseOrderList.stream().map(purchase -> modelMapper.map(purchase, PurchaseOrderDto.class)).toList();
    }

    public PurchaseOrderDto getPurchaseOrder(Long purchaseOrderId) {
        
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);

        if (!purchaseOrder.isPresent()) {
            throw new PurchaseOrderNotFound();
        }

        return modelMapper.map(purchaseOrder.get(), PurchaseOrderDto.class);

    }

    public PurchaseOrderDto createPurchaseOrder(PurchaseOrderDto requestBody) {

        if (requestBody.getPurchaseOrderId() != null) {
            throw new PurchaseOrderCreateError();
        }

        if (!purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, requestBody.getProductId()).isEmpty()) {
            throw new OpenPurchaseOrderExistsForProduct();
        }

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

    }

    public PurchaseOrderDto closePurchaseOrder(Long purchaseOrderId) {
        Optional<PurchaseOrder> optPurchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);

        if (!optPurchaseOrder.isPresent()) {
            throw new PurchaseOrderNotFound();
        }

        PurchaseOrder purchaseOrder = optPurchaseOrder.get();

        if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatusEnum.OPEN) {
            throw new PurchaseOrderNotOpen();
        }

        try {
            //Se actualiza estado de la orden de compra a cerrada.
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.CLOSED);
            purchaseOrderRepository.save(purchaseOrder);
            Optional<Product> optProduct = productRepository.findById(purchaseOrder.getProduct().getProductId());

            if (!optProduct.isPresent()) {
                throw new ProductNotFoundError();
            }

            //Se agrega stock de la orden de compra
            Product product = optProduct.get();
            product.addStock(purchaseOrder.getOrderQuantity());
            productRepository.save(product);


        } catch (Exception e) {
            throw new PurchaseOrderSaveError();
        }
        


        return modelMapper.map(purchaseOrder, PurchaseOrderDto.class);
    }

    public Collection<PurchaseOrderDto> getFixedIntervalPurchaseOrders() {        

        Collection<Product> productList = productRepository.findAllFixedIntervalProducts();

        if (productList.isEmpty() ) {
            throw new ProductNotFoundError();
        }

        List<PurchaseOrderDto> newPurhcaseOrders = productList.stream().map(p -> {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            //TODO: Revisar la cantidad de orden porque en realidad debería ser Stock máximo - Stock actual.
            //Aca supongo que el stock máximo lo defino con el lote óptimo, pero capaz tendría que ser una nueva variable?
            purchaseOrder.setOrderQuantity(p.calculateOptimalBatch());
            purchaseOrder.setProduct(p);
            purchaseOrder.setSupplier(p.getProductFamily().getSupplier());
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
            purchaseOrder.setPurchaseOrderDate(Date.from(Instant.now()));
            return modelMapper.map(purchaseOrder, PurchaseOrderDto.class) ;
        })
        .filter(p -> p.getOrderQuantity() > 0 
            && purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, p.getProductId()).isEmpty())
        .toList();

        if (newPurhcaseOrders.isEmpty()) {
            throw new NoneNewPurchaseOrdersError();
        }
        
        return newPurhcaseOrders;
    }

    public Object batchCreate(List<PurchaseOrderDto> newPurchaseOrders) {

        if (newPurchaseOrders.isEmpty()){
            throw new EmptyPurchaseOrderList();
        }

        for (PurchaseOrderDto purchaseOrder : newPurchaseOrders) {
            Optional<Product> optProduct = productRepository.findById(purchaseOrder.getProductId());
            if (!optProduct.isPresent()) {
                throw new ProductNotFoundError();
            }

            // Product product = optProduct.get();
            Optional<Supplier> optSupplier = supplierRepository.findById(purchaseOrder.getSupplierId());
            if (!optSupplier.isPresent()) {
                throw new SupplierNotFoundError();
            }
        
            PurchaseOrder newPurchaseOrder = new PurchaseOrder();
            newPurchaseOrder.setProduct(optProduct.get());
            newPurchaseOrder.setSupplier(optSupplier.get());
            newPurchaseOrder.setOrderQuantity(purchaseOrder.getOrderQuantity());
            newPurchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatusEnum.OPEN);
            newPurchaseOrder.setPurchaseOrderDate(Date.from(Instant.now().minus(3, ChronoUnit.HOURS)));

            purchaseOrderRepository.save(newPurchaseOrder);
        }

        return new DefaultResponseDto(HttpStatus.OK, "Objeto creado con éxito");
    }
}
