package com.pup.taguig.ordermanagement.service;

import com.pup.taguig.ordermanagement.dto.OrderRequestDTO;
import com.pup.taguig.ordermanagement.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    List<OrderResponseDTO> getAllOrders();

    List<OrderResponseDTO> getOrdersByCustomerId(Long customerId);

    OrderResponseDTO getOrderById(Long id);

    OrderResponseDTO createOrder(OrderRequestDTO request);

    boolean deleteOrder(Long id);
}