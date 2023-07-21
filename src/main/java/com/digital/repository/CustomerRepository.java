package com.digital.repository;

import com.digital.domain.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> , JpaSpecificationExecutor<Customer> {
    Optional<Customer> findOne(Specification<Customer> toSpec);

    @Query(value = "SELECT DISTINCT * FROM customer_contacts " +
            "INNER JOIN customer " +
            "ON customer_contacts.customer_cpf = customer.cpf " +
            "WHERE customer_contacts.contacts = :telephone ",
            nativeQuery = true)
    List<Customer> findByTelephone(@Param("telephone") String telephone);
}
