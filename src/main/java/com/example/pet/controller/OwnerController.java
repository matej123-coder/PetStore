package com.example.pet.controller;

import com.example.pet.domain.dto.CreateOwnersRequest;
import com.example.pet.domain.response.OwnerResponseDto;
import com.example.pet.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<OwnerResponseDto>> getUsers() {
        return ownerService.listOwners();
    }

    @PostMapping("/create-users")
    public ResponseEntity<List<OwnerResponseDto>> createUsers(@Valid @RequestBody CreateOwnersRequest createOwnersRequest) {
        return ownerService.createOwners(createOwnersRequest.getOwnerDtoList());
    }
}
