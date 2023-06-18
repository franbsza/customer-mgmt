package com.digital.application;

import com.digital.domain.Customer;
import com.digital.repository.CustomCustomerRepository;
import com.digital.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomCustomerRepository customCustomerRepository;

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(String cpf, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(cpf);

        if(customerOptional.isPresent()){
            Customer c = customerOptional.get();
            c.setName(customer.getName());
            c.setEmail(customer.getEmail());
            c.setBirthdate(customer.getBirthdate());
            c.setContacts(customer.getContacts());
            c.setAddress(customer.getAddress());
            return customerRepository.save(c);
        }
        return null;
    }

    public Optional<Customer> getCustomer(String field) {
        Specification<Customer> customQuery =  customCustomerRepository.searchByCpfOrEmail(field);
        return customerRepository.findOne(customQuery);
    }

    public List<Customer> getCustomerByTelefone(String telefone) {
        return customerRepository.findByTelephone(telefone);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public boolean delete(String id) {
        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
