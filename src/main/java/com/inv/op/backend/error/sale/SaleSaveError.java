package com.inv.op.backend.error.sale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Error saving sale")
public class SaleSaveError extends RuntimeException{

}
