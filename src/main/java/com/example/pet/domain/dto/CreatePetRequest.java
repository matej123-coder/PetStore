package com.example.pet.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreatePetRequest {
    @Valid
    @Size(max = 20, message = "You can not create more than 20 Pets")
    private List<@Valid PetDto> petDtoList;
}
