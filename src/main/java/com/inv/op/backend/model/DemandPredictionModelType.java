package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "demand_prediction_model_type")
@Data
@RequiredArgsConstructor
public class DemandPredictionModelType {

    @Id
    @JsonProperty(value = "demandPredictionModelTypeId")
    @Column(name = "demand_prediction_model_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demandPredictionModelTypeId;

    @JsonProperty(value = "demandPredictionModelTypeName")
    @Column(name = "demand_prediction_model_type_name", length = 30, nullable = false, unique = true)
    private String demandPredictionModelTypeName;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
