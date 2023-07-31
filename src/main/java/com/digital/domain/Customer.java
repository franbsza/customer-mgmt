package com.digital.domain;

import com.digital.infraestructure.validation.bithdate.BirthDate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name ="customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends RepresentationModel<Customer> {
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
    @ElementCollection
    private List<String> contacts;

    @Valid
    @NotNull
    @OneToOne(fetch = FetchType.EAGER ,cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
