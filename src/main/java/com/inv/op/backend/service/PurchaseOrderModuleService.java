package com.inv.op.backend.service;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.model.PurchaseOrder;
import com.inv.op.backend.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderModuleService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    ModelMapper modelMapper;

    public Collection<PurchaseOrderDto> getPurchaseOrderList() {
        // TODO Auto-generated method stub
        Collection<PurchaseOrder> purchaseOrderList= purchaseOrderRepository.findByPurchaseOrderStatusAndProductProductId(PurchaseOrderStatusEnum.OPEN, new Long(1));
        
        return purchaseOrderList.stream().map(purchase -> modelMapper.map(purchase, PurchaseOrderDto.class)).toList();
    }
    
}
