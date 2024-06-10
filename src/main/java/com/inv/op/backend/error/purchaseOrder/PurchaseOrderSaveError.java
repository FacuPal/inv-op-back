package com.inv.op.backend.error.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Hubo un error al guardar la nueva orden de compra") 
public class PurchaseOrderSaveError extends RuntimeException{

}
