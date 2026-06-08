package com.pup.taguig.ordermanagement.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pup.taguig.ordermanagement.dto.CustomerRequestDTO;
import com.pup.taguig.ordermanagement.dto.CustomerResponseDTO;
import com.pup.taguig.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public CustomerResponseDTO registerCustomer(@RequestBody CustomerRequestDTO request) {
        if (Objects.nonNull(request)) {
            return customerService.registerCustomer(request);
        }
        return null;
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        if (Objects.isNull(customer)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        return customer;
    }
}
