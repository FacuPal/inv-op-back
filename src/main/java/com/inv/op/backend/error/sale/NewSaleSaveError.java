package com.inv.op.backend.error.sale;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST, reason = "Error saving new sale")
public class NewSaleSaveError extends RuntimeException{
    
}
