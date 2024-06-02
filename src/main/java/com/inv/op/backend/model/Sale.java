package com.inv.op.backend.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inv.op.backend.dto.SaleDto;
import com.inv.op.backend.util.UpdateValidator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "sale")
@Data
public class Sale {

    @Id
    @JsonProperty(value = "saleId")
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    @JsonProperty(value = "customerName")
    @Column(name = "customer_name", length = 30, nullable = false)
    private String customerName;

    @JsonProperty(value = "saleDate")
    @Column(name = "sale_date", nullable = false)
    private Date saleDate;

    @JsonProperty(value = "product")
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "FK_sale_product"))
    private Product product;

    @JsonProperty(value = "quantity")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public Sale updateValues(SaleDto requestBody) {
        customerName = UpdateValidator.isEmptyOrNull(requestBody.getCustomerName()) ? customerName : requestBody.getCustomerName();
        saleDate = UpdateValidator.isEmptyOrNull(requestBody.getSaleDate()) ? saleDate : requestBody.getSaleDate();
        quantity = requestBody.getQuantity() == null ? quantity : requestBody.getQuantity();
        return this;
    }

}
