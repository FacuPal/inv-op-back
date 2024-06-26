package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "demand_prediction_model")
@Data
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class DemandPredictionModel {


    @Id
    @JsonProperty(value = "demandPredictionModelId")
    @Column(name = "demand_prediction_model_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandPredictionModelId;

    @JsonProperty(value = "demandPredictionModelColor")
    @Column(name = "demand_prediction_model_color", length = 30, nullable = false)
    private String demandPredictionModelColor;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "demand_prediction_model_type_id", nullable = false, referencedColumnName = "demand_prediction_model_type_id", foreignKey = @ForeignKey(name = "FK_demand_prediction_model_dpm_type"))
    @JsonProperty(value = "demandPredictionModelType")
    private DemandPredictionModelType demandPredictionModelType;

    @ManyToOne
    @JoinColumn(name = "demand_prediction_model_product_id", nullable = true, referencedColumnName = "product_id", foreignKey = @ForeignKey(name = "FK_demand_prediction_model_product"))
    @JsonProperty(value = "demandPredictionModelProduct")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "demand_prediction_model_product_family_id", nullable = true, referencedColumnName = "product_family_id", foreignKey = @ForeignKey(name = "FK_demand_prediction_model_product_family"))
    @JsonProperty(value = "demandPredictionModelProductFamily")
    private ProductFamily productFamily;

}
