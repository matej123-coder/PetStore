package com.example.pet.domain.response;

import lombok.Data;

import java.util.List;

@Data
public class OwnerResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Double budget;
    private List<PetResponseDto> pets;
}
