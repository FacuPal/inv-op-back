package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inv.op.backend.enums.PurchaseOrderStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@Entity
@Data
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id")
    @JsonProperty(value = "purchaseOrderId")
    private Long purchaseOrderId;

    @Column(name = "purchase_order_date")
    @JsonProperty(value = "purchaseOrderDate")
    private Date purchaseOrderDate;

    // @ManyToOne
    // @JoinColumn(name = "purchase_order_status_id", nullable = false, referencedColumnName = "purchase_order_status_id", foreignKey = @ForeignKey(name = "FK_purchase_order_purchase_order_status"))
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "orderStatus")
    private PurchaseOrderStatusEnum purchaseOrderStatus;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "FK_purchase_order_product"))
    @JsonProperty(value = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false, referencedColumnName = "supplier_id", foreignKey = @ForeignKey(name = "FK_purchase_order_supplier"))
    @JsonProperty(value = "supplier")
    private Supplier supplier;

    @JsonProperty(value = "orderQuantity")
    private Integer orderQuantity;  

}
