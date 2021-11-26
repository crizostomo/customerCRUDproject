package com.br.youtube.customer.ws.v1;

import com.br.youtube.customer.model.request.CustomerRequest;
import com.br.youtube.customer.model.response.CustomerResponse;
import com.br.youtube.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest customerRequest){
        LOGGER.info("Requesting received");
        return ResponseEntity.ok(customerService.create(customerRequest));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(Pageable pageable){
        LOGGER.info("Searching Registers");
        Page<CustomerResponse> customerResponses = customerService.getAll(pageable);
        return ResponseEntity.ok(customerResponses);
    }

//These are endpoints and they need to be accessed somehow

//We use responseEntity to "manipulate" the status that are going to return

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id") Long id, @RequestBody CustomerRequest customerRequest){
        LOGGER.info("Initializing update");
        Optional<CustomerResponse> update = customerService.update(id, customerRequest);
        if(!update.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(update.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> get(@PathVariable("id") Long id){
        LOGGER.info("Starting searches for registers");
        Optional<CustomerResponse> customerResponse = customerService.get(id);
        if(!customerResponse.isPresent()){ //Press 'CTRL+ENTER' to see Lambda <---
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerResponse.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        LOGGER.info("Starting register removing");
        if(customerService.delete(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
