package com.inv.op.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "parameter")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Parameter {

    @Id
    @JsonProperty(value = "parameterId")
    @Column(name = "parameter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parameterId;

    @JsonProperty(value = "parameterName")
    @Column(name = "parameter_name", length = 30, nullable = false, unique = true)
    private String parameterName;

    @JsonProperty(value = "parameterValue")
    @Column(name = "parameter_value", length = 256, nullable = true)
    private String parameterValue;

    @JsonProperty(value = "isDeleted")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}
