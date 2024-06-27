package com.inv.op.backend.error.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Ya existe una orden de compra abierta para el producto seleccionado")
public class OpenPurchaseOrderExistsForProduct extends RuntimeException{

}
