package com.br.youtube.customer.service;

import com.br.youtube.customer.model.request.CustomerRequest;
import com.br.youtube.customer.model.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface CustomerService {
    CustomerResponse create(CustomerRequest customerRequest);

    Page<CustomerResponse> getAll(Pageable pageable);
//Page helps to improve the performance and it lists all

    Optional<CustomerResponse> update(Long id, CustomerRequest customerRequest);

    Optional<CustomerResponse> get(Long id);
//get selects only one

    boolean delete(Long id);

}
