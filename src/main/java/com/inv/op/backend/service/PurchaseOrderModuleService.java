package com.inv.op.backend.service;

import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.error.purchaseOrder.PurchaseOrderNotFound;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderModuleService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ModelMapper modelMapper;

    public Collection<PurchaseOrderDto> getPurchaseOrderList() {

        Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, new Long(1));
        
        return purchaseOrderList.stream().map(purchase -> modelMapper.map(purchase, PurchaseOrderDto.class)).toList();
    }

    public PurchaseOrderDto getPurchaseOrder(Long purchaseOrderId) {
        
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);

        if (!purchaseOrder.isPresent()) {
            throw new PurchaseOrderNotFound();
        }

        return modelMapper.map(purchaseOrder.get(), PurchaseOrderDto.class);

    }
    
}
