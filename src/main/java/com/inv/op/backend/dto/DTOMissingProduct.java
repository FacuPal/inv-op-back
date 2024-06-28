package com.inv.op.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTOMissingProduct {
        public Long id;
        public String name;
        public int stock;
        public int missingAmount;

        public DTOMissingProduct(Long id, String name, int stock, int missingAmount) {
            this.id = id;
            this.name = name;
            this.stock = stock;
            this.missingAmount = missingAmount;
        }

}
