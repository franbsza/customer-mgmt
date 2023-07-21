package com.digital.application;

import com.digital.domain.Customer;
import com.digital.interfaces.rest.CustomerController;
import com.digital.repository.CustomCustomerRepository;
import com.digital.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomCustomerRepository customCustomerRepository;

    public Customer create(Customer customer) {
        Link selfLink = linkTo(CustomerController.class).slash(customer.getCpf()).withSelfRel();
        Link allCustomers = linkTo(methodOn(CustomerController.class).getAll()).withRel("all customers");
        return customerRepository.save(customer).add(selfLink).add(allCustomers);
    }

    public Customer update(Customer customer) {
        Optional<Customer> customerResponse = customerRepository.findById(customer.getCpf());

        if(customerResponse.isPresent()){
            Customer c = customerResponse.get();
            c.setCpf(customer.getCpf());
            c.setName(customer.getName());
            c.setEmail(customer.getEmail());
            c.setBirthdate(customer.getBirthdate());
            c.setContacts(customer.getContacts());
            c.setAddress(customer.getAddress());

            Link selfLink = linkTo(CustomerController.class).slash(c.getCpf()).withSelfRel();

            return customerRepository.save(c).add(selfLink);
        }
        return null;
    }

    public Optional<Customer> getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);

        Link allCustomers = linkTo(methodOn(CustomerController.class)
                .getAll()).withRel("all customers");

        return customer.map(value -> value.add(
                        linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel())
                .add(allCustomers));
    }

    public Optional<Customer> getCustomer(String... args) {
        String arg = Arrays.stream(args)
                .filter(Objects::nonNull)
                .findFirst().orElseThrow(
                        () -> new IllegalArgumentException("Invalid Arguments"));

        Specification<Customer> customQuery =  customCustomerRepository.searchByCpfOrEmail(arg);
        Optional<Customer> customer = customerRepository.findOne(customQuery);

        Link allCustomers = linkTo(methodOn(CustomerController.class)
                .getAll()).withRel("all customers");

        return customer.map(value -> value.add(
                        linkTo(methodOn(CustomerController.class).getCustomerById(value.getCpf())).withSelfRel())
                .add(allCustomers));
    }

    public List<Customer> getCustomerByTelefone(String telefone) {

        return customerRepository.findByTelephone(telefone).stream()
                .map(customer -> customer.add(linkTo(CustomerController.class)
                        .slash(telefone)
                        .withSelfRel()))
                .toList();
    }

    public List<Customer> getAll() {
        return customerRepository.findAll().stream()
                .map(customer -> customer.add(linkTo(CustomerController.class)
                        .slash(customer.getCpf())
                        .withSelfRel()))
                .toList();
    }

    public boolean delete(String id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
