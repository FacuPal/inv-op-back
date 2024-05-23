package com.inv.op.backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Product") 
public class ProductNotFoundError extends RuntimeException {
    
}
