package com.example.pet.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OwnerDto {
    @NotEmpty(message = "First name can not be empty")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    private String lastName;
    @Email(message = "Enter a valid email")
    private String email;
    @Positive(message = "Budget must be a positive number")
    @NotNull(message = "Budget can not be empty")
    private Double budget;
    private List<PetDto> petDtos;

}
