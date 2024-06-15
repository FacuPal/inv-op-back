package com.inv.op.backend.error.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No existe la familia de producto solicitada") 
public class ProductFamilyNotFound extends RuntimeException {
    
}
