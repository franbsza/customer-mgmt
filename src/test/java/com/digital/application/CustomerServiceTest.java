package com.digital.application;

import com.digital.domain.Address;
import com.digital.domain.Customer;
import com.digital.interfaces.rest.CustomerController;
import com.digital.repository.CustomCustomerRepository;
import com.digital.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.hateoas.Link;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    CustomerRepository customerRepository;
    CustomCustomerRepository customCustomerRepository;
    CustomerService customerService;
    Customer customer;
    Customer customerResponse;

    @BeforeAll
    public void setup() {
        customerRepository = mock(CustomerRepository.class);
        customCustomerRepository = mock(CustomCustomerRepository.class);
        customerService = new CustomerService(customerRepository, customCustomerRepository);

        customer = Customer.builder()
                .cpf("06630404009")
                .name("Maria")
                .email("ddd@gmail.com")
                .birthdate("01/01/1990")
                .contacts(Arrays.asList("35999999999", "3522222222"))
                .address(Address.builder()
                        .addressDetail("Oxford street")
                        .city("Westminster")
                        .zipcode("173")
                        .country("London")
                        .build())
                .build();

        Link selfLink = linkTo(CustomerController.class).slash(customer.getCpf()).withSelfRel();
        Link allCustomers = linkTo(methodOn(CustomerController.class).getAll()).withRel("all customers");
        customerResponse = Customer.builder()
                .cpf("06630404009")
                .name("Maria")
                .email("ddd@gmail.com")
                .birthdate("01/01/1990")
                .contacts(Arrays.asList("35999999999", "3522222222"))
                .address(Address.builder()
                        .addressDetail("Oxford street")
                        .city("Westminster")
                        .zipcode("173")
                        .country("London")
                        .build())
                .build();
        customerResponse.add(selfLink).add(allCustomers);
    }

    @Test
    @DisplayName("should create new customer")
    void createCustomerTest(){

        when(customerRepository.save(customer)).thenReturn(customerResponse);
        assertEquals(customerResponse,  customerService.create(customer));
    }

    @Test
    @DisplayName("should find a customer by cpf")
    void getCustomerById(){
        when(customerRepository.findById(customer.getCpf())).thenReturn(Optional.ofNullable(customerResponse));
        assertEquals(Optional.of(customerResponse) ,
                customerService.getCustomerById("06630404009"));
    }

    @Test
    @DisplayName("should return all customers")
    void getAllCustomer(){
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customerResponse));
        assertEquals(Collections.singletonList(customerResponse),
                customerService.getAll());
    }

    @Test
    @DisplayName("should delete a customer")
    void deleteCustomer(){
        when(customerService.delete("06630404009")).thenReturn(Boolean.TRUE);
        assertTrue(customerService.delete("06630404009"));
    }
}
