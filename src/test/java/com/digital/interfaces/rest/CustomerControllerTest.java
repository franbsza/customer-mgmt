package com.digital.interfaces.rest;

import com.digital.application.CustomerService;
import com.digital.domain.Address;
import com.digital.domain.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;
    Customer customer;
    Customer customerResponse;

    @BeforeAll
    public void setup() {
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
    @DisplayName("should create customer successfully")
    void createOrder() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        when(customerService.create(customer)).thenReturn(customerResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("should return 400 - bad request")
    void argumentNotInformed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
