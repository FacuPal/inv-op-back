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
@DiscriminatorValue("Ix")
public class IxDemandPredictionModel extends DemandPredictionModel {

    @JsonProperty(value = "length")
    @Column(name = "length", nullable = true)
    private Integer length;

    @JsonProperty(value = "count")
    @Column(name = "count", nullable = true)
    private Integer count;

    @JsonProperty(value = "expectedDemand")
    @Column(name = "expected_demand", nullable = true)
    private Integer expectedDemand;
}
