package com.inv.op.backend.error.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No existe el producto solicitado") 
public class ProductNotFoundError extends RuntimeException {
    
}
