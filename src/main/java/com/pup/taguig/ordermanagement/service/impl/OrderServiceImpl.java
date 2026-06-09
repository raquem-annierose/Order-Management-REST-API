package com.pup.taguig.ordermanagement.service.impl;

import com.pup.taguig.ordermanagement.dto.OrderItemRequestDTO;
import com.pup.taguig.ordermanagement.dto.OrderRequestDTO;
import com.pup.taguig.ordermanagement.dto.OrderResponseDTO;
import com.pup.taguig.ordermanagement.model.Order;
import com.pup.taguig.ordermanagement.model.OrderItem;
import com.pup.taguig.ordermanagement.model.Product;
import com.pup.taguig.ordermanagement.repository.CustomerRepository;
import com.pup.taguig.ordermanagement.repository.OrderRepository;
import com.pup.taguig.ordermanagement.repository.ProductRepository;
import com.pup.taguig.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        }

        return toDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        if (request == null || request.getCustomerId() == null || request.getItems() == null || request.getItems().isEmpty()) {
            return null;
        }

        var customerOpt = customerRepository.findById(request.getCustomerId());
        if (customerOpt.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setCustomer(customerOpt.get());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemReq : request.getItems()) {
            if (itemReq == null || itemReq.getProductId() == null || itemReq.getQuantity() == null || itemReq.getQuantity() <= 0) {
                return null;
            }

            Product product = productRepository.findById(itemReq.getProductId()).orElse(null);
            if (product == null) {
                return null;
            }

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setOrder(order);

            BigDecimal qty = BigDecimal.valueOf(itemReq.getQuantity());
            total = total.add(product.getPrice().multiply(qty));

            items.add(item);
        }

        order.setItems(items);
        order.setTotalPrice(total);

        Order saved = orderRepository.save(order);

        return toDTO(saved);
    }

    @Override
    @Transactional
    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return false;
        }

        orderRepository.delete(order);
        return true;
    }

    private OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }
}