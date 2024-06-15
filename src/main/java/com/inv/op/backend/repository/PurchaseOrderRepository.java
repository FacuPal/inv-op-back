package com.inv.op.backend.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inv.op.backend.dto.PurchaseOrderDto;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;
import com.inv.op.backend.model.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends ListCrudRepository<PurchaseOrder,Long> {

    // @Query("SELECT p " +
    //         "FROM PurchaseOrder p" +
    //         "WHERE p.productId = :productId " +
    //         "AND p.purchaseOrderStatus = :purchaseOrderStatus")
    // Optional<PurchaseOrder> findByProductAndPurchaseOrderStatus(Long productId, PurchaseOrderStatusEnum purchaseOrderStatus);

    Collection<PurchaseOrder> findByPurchaseOrderStatus( PurchaseOrderStatusEnum purchaseOrderStatusEnum);

    Collection<PurchaseOrder> findByPurchaseOrderStatusAndProductProductId( PurchaseOrderStatusEnum purchaseOrderStatusEnum, Long productId);

    
}
