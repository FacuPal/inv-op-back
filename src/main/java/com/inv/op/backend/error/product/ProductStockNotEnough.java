package com.inv.op.backend.error.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No hay suficiente stock para registrar la venta.") 
public class ProductStockNotEnough extends RuntimeException {

}
