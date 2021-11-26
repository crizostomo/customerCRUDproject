package com.br.youtube.customer.service.mapper;

import com.br.youtube.customer.model.response.CustomerResponse;
import com.br.youtube.customer.persistence.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerResponseMapper implements Mapper<Customer, CustomerResponse> {
    @Override
    public CustomerResponse map(Customer input) {
        if(input == null) {
            return null;
        }

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(input.getId());
        customerResponse.setName(input.getName());
        customerResponse.setDocument(input.getDocument());

        return customerResponse;
    }
}
