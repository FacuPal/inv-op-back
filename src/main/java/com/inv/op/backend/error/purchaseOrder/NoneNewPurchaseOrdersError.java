package com.inv.op.backend.error.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No hay nuevas ordenes de compra para generar.")
public class NoneNewPurchaseOrdersError extends RuntimeException{

}
