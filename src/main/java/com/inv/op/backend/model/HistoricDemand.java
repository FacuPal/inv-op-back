package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "historic_demand", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"year", "month", "product_id"}, name = "UK_historic_demand_year_month_product_id")
})
@Data
@RequiredArgsConstructor
public class HistoricDemand {

    @Id
    @JsonProperty(value = "historicDemandId")
    @Column(name = "historic_demand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historicDemandId;

    @JsonProperty(value = "year")
    @Min(value = 1900, message = "Year must be greater than 1900")
    @Max(value = 2024, message = "Year must be less than 2024")
    @Column(name = "year", nullable = false)
    private Integer year;

    @JsonProperty(value = "month")
    @Column(name = "month", nullable = false)
    @Min(value = 1, message = "Month must be greater than 1")
    @Max(value = 12, message = "Month must be less than 12")
    private Integer month;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "FK_historic_demand_product"))
    @JsonProperty(value = "product")
    private Product product;    

    @JsonProperty(value = "quantity")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public HistoricDemand addDemand(Integer demand){
        quantity += demand;
        return this;
    }

    public HistoricDemand reduceDemand(Integer demand){
        quantity -= demand;
        return this;
    }
}
