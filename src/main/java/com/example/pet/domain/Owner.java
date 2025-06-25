package com.example.pet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Double budget;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    private List<Pet> pets;

    public Owner(String firstName, String lastName, String email, Double budget) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.budget = budget;
    }

}
