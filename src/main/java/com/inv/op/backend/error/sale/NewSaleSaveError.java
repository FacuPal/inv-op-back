package com.inv.op.backend.error.sale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error saving new sale")
public class NewSaleSaveError extends RuntimeException{
    
}
