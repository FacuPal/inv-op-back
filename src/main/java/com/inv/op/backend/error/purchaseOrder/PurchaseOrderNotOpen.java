package com.inv.op.backend.error.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La orden de compra ya está cerrada.")
public class PurchaseOrderNotOpen extends RuntimeException{

}
