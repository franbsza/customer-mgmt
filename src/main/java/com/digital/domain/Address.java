package com.digital.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@Table(name ="address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    private String addressDetail;
    @NotEmpty
    private String city;
    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String country;
}
