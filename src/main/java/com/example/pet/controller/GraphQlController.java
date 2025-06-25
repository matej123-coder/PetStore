package com.example.pet.controller;

import com.example.pet.domain.dto.CreateOwnersRequest;
import com.example.pet.domain.dto.CreatePetRequest;
import com.example.pet.domain.dto.OwnerDto;
import com.example.pet.domain.dto.PetDto;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import com.example.pet.domain.response.OwnerResponseDto;
import com.example.pet.domain.response.PetResponseDto;
import com.example.pet.service.OwnerService;
import com.example.pet.service.PetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQlController {
    private final OwnerService ownerService;
    private final PetService petService;

    public GraphQlController(OwnerService ownerService, PetService petService) {
        this.ownerService = ownerService;
        this.petService = petService;
    }


    @QueryMapping
    public List<OwnerResponseDto> owners() {
        return ownerService.listOwners().getBody();
    }

    @QueryMapping
    public List<PetResponseDto> pets() {
        return petService.listPet().getBody();
    }

    @QueryMapping
    public List<BuyHistoryLogResponse> historyLog() {
        return petService.showHistoryLogs().getBody();
    }

    @MutationMapping
    public List<OwnerResponseDto> createOwners(@Argument CreateOwnersRequest input) {
        return ownerService.createOwners(input.getOwnerDtoList()).getBody();
    }

    @MutationMapping
    public List<PetResponseDto> createPets(@Argument CreatePetRequest input) {
        return petService.createPet(input.getPetDtoList()).getBody();
    }

    @MutationMapping
    public List<String> buyPets() {
        return petService.buyPets().getBody();
    }
}
