package com.inv.op.backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Error saving product") 
public class ProductSaveError extends RuntimeException {
    
}
