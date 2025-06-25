package com.example.pet.controller;

import com.example.pet.domain.dto.CreatePetRequest;
import com.example.pet.domain.response.BuyHistoryLogResponse;
import com.example.pet.domain.response.PetResponseDto;
import com.example.pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetResponseDto>> getPets() {
        return petService.listPet();
    }

    @PostMapping("/create-pets")
    public ResponseEntity<List<PetResponseDto>> createPets(@Valid @RequestBody CreatePetRequest createPetRequest) {
        return petService.createPet(createPetRequest.getPetDtoList());
    }

    @PostMapping("/buy")
    public ResponseEntity<List<String>> buyPets() {
        return petService.buyPets();
    }

    @GetMapping("/history-log")
    public ResponseEntity<List<BuyHistoryLogResponse>> getLogs() {
        return petService.showHistoryLogs();
    }
}
