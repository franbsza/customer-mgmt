package com.digital.interfaces.rest;

import com.digital.application.CustomerService;
import com.digital.domain.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService service;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@RequestBody @Valid Customer customer){
        Customer customerResponse = service.create(customer);
        if(customerResponse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> update(@RequestBody @Valid Customer customer){
        Customer customerResponse = service.update(customer);
        if(customerResponse == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customerResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id){

        return ResponseEntity.of(service.getCustomerById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@RequestHeader(value = "cpf", required = false) String cpf,
                                                @RequestHeader(value = "email", required = false) String email){

        if(cpf == null && email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.of(service.getCustomer(cpf, email));
    }

    @GetMapping(path = {"/telephone"})
    public ResponseEntity<List<Customer>> getCustomerByTelephone(@RequestHeader(value = "telephone") String telephone){

        List<Customer> customer = service.getCustomerByTelefone(telephone);

        if(!customer.isEmpty()){
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path = {"/all"})
    public ResponseEntity<List<Customer>> getAll(){

        List<Customer> customers = service.getAll();

        if(!customers.isEmpty()){
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestHeader String cpf){
        if(service.delete(cpf)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
