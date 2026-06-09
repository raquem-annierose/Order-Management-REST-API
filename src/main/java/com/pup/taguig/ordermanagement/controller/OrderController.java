package com.pup.taguig.ordermanagement.controller;

import com.pup.taguig.ordermanagement.dto.OrderRequestDTO;
import com.pup.taguig.ordermanagement.dto.OrderResponseDTO;
import com.pup.taguig.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        OrderResponseDTO order = orderService.getOrderById(id);

        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }

        return order;
    }

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO created = orderService.createOrder(request);
        if (created == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order request");
        }
        return created;
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}