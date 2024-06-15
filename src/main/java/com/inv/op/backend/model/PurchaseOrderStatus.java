// package com.inv.op.backend.model;

// import com.fasterxml.jackson.annotation.JsonProperty;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import lombok.Data;

// @Entity
// @Data
// @Table(name = "purchase_order_status")
// public class PurchaseOrderStatus {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "purchase_order_status_id")
//     @JsonProperty(value = "purchaseOrderStatusId")
//     private Long purchaseOrderStatusId;

//     @Column(name = "purchase_order_status_name", nullable = false, length = 30)
//     @JsonProperty(value = "purchaseOrderStatusName")
//     private String purchaseOrderStatusName;

// }
