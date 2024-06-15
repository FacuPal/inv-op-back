package com.inv.op.backend.error.sale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Sale") 
public class SaleNotFoundError extends RuntimeException {
    
}
