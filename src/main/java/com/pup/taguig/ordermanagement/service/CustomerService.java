package com.pup.taguig.ordermanagement.service;

import com.pup.taguig.ordermanagement.dto.CustomerRequestDTO;
import com.pup.taguig.ordermanagement.dto.CustomerResponseDTO;

public interface CustomerService {

    CustomerResponseDTO registerCustomer(CustomerRequestDTO request);

    CustomerResponseDTO getCustomerById(Long id);
}
