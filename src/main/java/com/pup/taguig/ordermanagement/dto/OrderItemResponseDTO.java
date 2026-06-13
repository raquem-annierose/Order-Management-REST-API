package com.pup.taguig.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponseDTO {
    private Long productId;
    private int quantity;
    private BigDecimal unitPrice;
}