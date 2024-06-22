package com.inv.op.backend.error.purchaseOrder;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No envió ninguna orden de compra para crear")
public class EmptyPurchaseOrderList extends RuntimeException {

}
