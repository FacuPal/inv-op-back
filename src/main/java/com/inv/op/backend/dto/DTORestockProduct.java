package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTORestockProduct {
Long idRestockProduct;
String nameRestockProduct;
Integer optimalBatch;
}
