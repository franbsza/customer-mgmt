package com.digital.repository;

import com.digital.domain.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomCustomerRepository {
    public Specification<Customer> searchByCpfOrEmail(String cpf, String email){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(cpf)){
                predicates.add(
                        criteriaBuilder.equal(root.get("cpf"), cpf));
            }
            if (StringUtils.hasText(email)){
                predicates.add(
                        criteriaBuilder.equal(root.get("email"), email));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
