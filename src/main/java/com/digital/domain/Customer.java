package com.digital.domain;

import com.digital.infraestructure.validation.bithdate.BirthDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name ="customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @NotEmpty
    private String cpf;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @Valid
    @BirthDate
    @NotEmpty
    private String birthdate;

    @Valid
    @NotEmpty
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cpf")
    private List<Contact> contacts;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER ,cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
