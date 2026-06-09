package com.pup.taguig.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDTO {

    private Long productId;
    private Integer quantity;
}
