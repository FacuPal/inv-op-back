package com.inv.op.backend.error.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Hubo un error al guardar el producto") 
public class ProductSaveError extends RuntimeException {
    
}
