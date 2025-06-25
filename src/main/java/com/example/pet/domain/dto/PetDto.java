package com.example.pet.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
    @NotEmpty(message = "Pet name can not be empty")
    private String name;

    @NotEmpty(message = "Type must be either CAT or DOG")
    @Pattern(regexp = "Cat|Dog", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Type must be Cat or Dog")
    private String type;
    @NotEmpty(message = "Description can not be empty")
    private String description;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    // Only used if type is DOG
    @Min(value = 0, message = "Rating must be between 0 and 10")
    @Max(value = 10, message = "Rating must be between 0 and 10")
    private Integer rating;

}
