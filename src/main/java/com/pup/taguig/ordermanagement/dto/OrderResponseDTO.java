package com.pup.taguig.ordermanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    
    private List<OrderItemResponseDTO> items; 
}