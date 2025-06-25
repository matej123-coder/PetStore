package com.example.pet.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateOwnersRequest {
    @Valid
    @Size(max = 10, message = "You have created more than 10 users please try to create less than or equal to 10")
    private List<@Valid OwnerDto> ownerDtoList;
}
