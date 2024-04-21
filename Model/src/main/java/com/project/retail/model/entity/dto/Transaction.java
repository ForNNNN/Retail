package com.project.retail.model.entity.dto;

import lombok.Data;

@Data
public class Transaction {
    private Long userId;
    private Long productId;
    private Double totalPrice;
}
