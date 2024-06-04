package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
@DiscriminatorValue("RL")
public class RLDemandPredicitionModel extends DemandPredictionModel {

    @JsonProperty(value = "ignorePeriods")
    @Column(name = "ignore_periods", nullable = true)
    private Integer ignorePeriods;
}
