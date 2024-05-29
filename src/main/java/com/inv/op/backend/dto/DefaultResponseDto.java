package com.inv.op.backend.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DefaultResponseDto implements Serializable{
    private HttpStatus statusCode;
    private String data;   
}
