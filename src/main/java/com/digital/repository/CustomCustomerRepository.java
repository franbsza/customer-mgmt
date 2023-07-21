package com.digital.repository;

import com.digital.domain.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomCustomerRepository {
    public Specification<Customer> searchByCpfOrEmail(String field){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(field)){
                if(field.contains("@")){
                    predicates.add(
                            criteriaBuilder.equal(root.get("email"), field));
                }
                else{
                    predicates.add(
                            criteriaBuilder.equal(root.get("cpf"), field));
                }

            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
