package com.inv.op.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOError {
    @JsonProperty(value = "mensaje")
    String mensaje = "";
}
