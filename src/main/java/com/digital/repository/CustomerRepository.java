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
    @Query("SELECT (c) FROM Customer c WHERE (c.name) like %:name% ")
    List<Customer> findByName(@Param("name") String name);

    Optional<Customer> findOne(Specification<Customer> toSpec);

    @Query(value = "SELECT DISTINCT * FROM customer cs INNER JOIN contact ctt ON cs.cpf = ctt.cpf WHERE ctt.telephone = :telephone ", nativeQuery = true)
    List<Customer> findByTelephone(@Param("telephone") String telephone);
}
