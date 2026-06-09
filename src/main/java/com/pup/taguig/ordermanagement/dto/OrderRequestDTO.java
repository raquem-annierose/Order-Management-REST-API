package com.pup.taguig.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    private Long customerId;
    private List<OrderItemRequestDTO> items;
}
