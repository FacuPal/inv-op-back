package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
@DiscriminatorValue("PMSE")
public class PMSEDemandPredictionModel extends DemandPredictionModel {

    @JsonProperty(value = "alpha")
    @Column(name = "alpha", nullable = true)
    private Double alpha;

    @JsonProperty(value = "root")
    @Column(name = "root", nullable = true)
    private Integer root;

}
