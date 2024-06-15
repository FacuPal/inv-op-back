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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity
@Table(name = "next_period_prediction")
@Data
public class NextPeriodPrediction {

    @Id
    @JsonProperty(value = "nextPeriodPredictionId")
    @Column(name = "next_period_prediction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nextPeriodPredictionId;

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
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "FK_next_period_prediction_product"))
    @JsonProperty(value = "product")
    private Product product;    

    @JsonProperty(value = "quantity")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @ManyToOne(targetEntity = DemandPredictionModel.class)
    @JoinColumn(name = "demand_prediction_model_id", nullable = false, referencedColumnName = "demand_prediction_model_id", foreignKey = @ForeignKey(name = "FK_next_period_prediction_demand_prediction_model"))
    @JsonProperty(value = "demandPredictionModel")
    private DemandPredictionModel demandPredictionModel;

}
