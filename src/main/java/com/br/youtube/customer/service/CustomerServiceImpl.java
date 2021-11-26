package com.br.youtube.customer.service;

import com.br.youtube.customer.model.request.CustomerRequest;
import com.br.youtube.customer.model.response.CustomerResponse;
import com.br.youtube.customer.persistence.entity.Customer;
import com.br.youtube.customer.persistence.repository.CustomerRepository;
import com.br.youtube.customer.service.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.springframework.util.Assert.*;

//Here we define and describe how our services work, we define rules

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
//We use this line above to log in our actions in our apllications

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Mapper<CustomerRequest, Customer> requestMapper;

    @Autowired
    private Mapper<Customer, CustomerResponse> responseMapper;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        LOGGER.info("Creating a client register");
        notNull(customerRequest, "Invalid Request");
        Customer customer = this.requestMapper.map(customerRequest);
        return customerRepository.saveAndFlush(customer).map((Customer input) -> this.responseMapper.map(input));
    }

    @Override
    public Page<CustomerResponse> getAll(Pageable pageable) {
        LOGGER.info("Searching all registers");
        notNull(pageable, "invalid page");
        return customerRepository.findAll(pageable).map(customer -> this.responseMapper.map(customer));
    }

    @Override
    public Optional<CustomerResponse> update(Long id, CustomerRequest customerRequest) {
        LOGGER.info("Updating register");
        notNull(id, "Invalid ID");

        Customer customerUpdate = this.requestMapper.map(customerRequest);

//        Optional<Customer> customer = customerRepository.findById(id);
//It was typed 'customerRepository.findById(id);' and after this 'CTRL+ALT+V'
//        customer.get().setName(customerUpdate.getName());
//        customer.get().setDocument(customerUpdate.getDocument());
//        ...

        return customerRepository.findById(id)
                .map(customer -> {
//                    customer.setDocument(customerUpdate.getDocument());
                    customer.setName(customerUpdate.getName());
                    return this.responseMapper.map(customerRepository.saveAndFlush(customer));
                });
    }

    @Override
    public Optional<CustomerResponse> get(Long id) {
        LOGGER.info("Searching Register");
        notNull(id, "Invalid ID");
        return customerRepository.findById(id).map(this.responseMapper::map);
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Removing Register");
        notNull(id, "Invalid ID");

        try{
            customerRepository.deleteById(id);
            return true;
        }catch (Exception e){
            LOGGER.warn("Error when removing register {}", id);
        }

        return false;
    }
}
