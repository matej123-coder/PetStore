package com.example.pet.domain.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PetResponseDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private LocalDate dateOfBirth;
    private Double price;
    private Integer rating;
    private OwnerResponseDto ownerResponseDto;
}
